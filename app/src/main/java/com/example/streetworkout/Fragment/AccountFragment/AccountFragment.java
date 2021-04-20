package com.example.streetworkout.Fragment.AccountFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.example.streetworkout.User.UserInfor;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import java.util.Objects;


public class AccountFragment extends Fragment {
    public static UserInfor userInfor;
    //recycler view
    RecyclerView recyclerView;
    ArrayList<StatusWorkout> listStatus;
    AccountFragmentAdapter accountFragmentAdapter;
    boolean isEditProfile= false;
    View root;
    public AccountFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.main_fragment_account, container, false);
        // Setup Infor
        SetupInfor();
        // Setup Event
        SetupCLickEvent();

        return root;
    }

    private void SetupInfor(){
        // Set Avatar Profile
        //
        switch (userInfor.getLoginTypes()){
            case 1:
                Glide.with(this.getActivity().getApplicationContext()).load(Uri.parse(userInfor.getUrlAvatar())).into((ImageView)root.findViewById(R.id.avatarProfile));
                break;
            case 2:
                GraphRequest request = GraphRequest.newGraphPathRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/" + userInfor.getUrlAvatar().replaceAll("\\D+","") + "/picture?redirect=0&type=large",
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                JSONObject result = response.getJSONObject();
                                try {
                                    String link = result.getJSONObject("data").getString("url").toString();
                                    Glide.with(getActivity().getApplicationContext()).load(Uri.parse(link)).into((ImageView)root.findViewById(R.id.avatarProfile));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                request.executeAsync();
                break;
        }

        // Name
        TextView nameDislay = root.findViewById(R.id.nameDislay);
        nameDislay.setText(userInfor.getDisplayName());
        // Username
        TextView userName = root.findViewById(R.id.userName);
        userName.setText(userInfor.getUserName());
        // Workout Success
        TextView workoutSucess = root.findViewById(R.id.workoutShow);
        workoutSucess.setText(String.valueOf(userInfor.getStatus().getWorkoutStatus()));
        // Follower
        TextView follower = root.findViewById(R.id.followerShow);
        follower.setText(String.valueOf(userInfor.getStatus().getFollowersStatus()));
        // Following
        TextView following = root.findViewById(R.id.followingShow);
        following.setText(String.valueOf(userInfor.getStatus().getFollowingStatus()));
        recyclerView = root.findViewById(R.id.recyclerView);
        listStatus = new ArrayList<>();

        SetStatus();

        accountFragmentAdapter = new AccountFragmentAdapter(AccountFragment.this.getContext(),listStatus);
        recyclerView.setAdapter(accountFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountFragment.this.getContext()));
    }

    private void SetupCLickEvent(){
        // EditProfile
        Button editProfile = root.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditProfile) return;
                isEditProfile = true;
                EditProfile_Click(v);
                isEditProfile = false;
            }
        });
    }

    public void EditProfile_Click(View view){
        Intent editProfile = new Intent(getActivity(),AccountEditProfile.class);
        editProfile.putExtra("userProfile",userInfor);
        getActivity().startActivityForResult(editProfile, MainActivity.RC_EDITPROFILE);
        getActivity().overridePendingTransition(R.anim.from_bottom_up_light,R.anim.to_top_light);
    }

    public void SetStatus()
    {
        FirebaseDatabase.getInstance().getReference().child("StatusUserExercise").orderByChild("uid").equalTo(userInfor.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StatusWorkout k = snapshot.getValue(StatusWorkout.class);
                k.setUserInfor(userInfor);
                listStatus.add(k);
                accountFragmentAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

}