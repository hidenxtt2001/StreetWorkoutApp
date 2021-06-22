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
    TextView workoutSucess;
    private void SetupInfor(){
        // Set Avatar Profile
        //
        switch (MainActivity.userInfor.getLoginTypes()){
            case 1:
                Glide.with(this.getActivity().getApplicationContext()).load(Uri.parse(MainActivity.userInfor.getUrlAvatar())).into((ImageView)root.findViewById(R.id.avatarProfile));
                break;
            case 2:
                GraphRequest request = GraphRequest.newGraphPathRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/" + MainActivity.userInfor.getUrlAvatar().replaceAll("\\D+","") + "/picture?redirect=0&type=large",
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

        SetupDisplay();
        // Workout Success


        recyclerView = root.findViewById(R.id.recyclerView);
        listStatus = new ArrayList<>();

        SetStatus();

        accountFragmentAdapter = new AccountFragmentAdapter(AccountFragment.this.getContext(),listStatus);
        recyclerView.setAdapter(accountFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountFragment.this.getContext()));
    }

    public void SetupDisplay(){
        // Name
        TextView nameDislay = root.findViewById(R.id.nameDislay);
        nameDislay.setText(MainActivity.userInfor.getDisplayName());
        // Username
        TextView userName = root.findViewById(R.id.userName);
        userName.setText(MainActivity.userInfor.getUserName());
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
        getActivity().startActivityForResult(editProfile, MainActivity.RC_EDITPROFILE);
        getActivity().overridePendingTransition(R.anim.from_bottom_up_light,R.anim.to_top_light);
    }
    int count_workout =0;
    public void SetStatus()
    {
        FirebaseDatabase.getInstance().getReference().child("StatusUserExercise").orderByChild("uid").equalTo(MainActivity.userInfor.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StatusWorkout k = snapshot.getValue(StatusWorkout.class);
                k.setUserInfor(MainActivity.userInfor);
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