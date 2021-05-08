package com.example.streetworkout.Fragment.AccountFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;

import java.util.Objects;


public class AccountFragment extends Fragment {

    View root;
    public static UserInfor userInfor;
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
        Glide.with(this.getActivity().getApplicationContext()).load(Uri.parse(userInfor.getUrlAvatar())).into((ImageView)root.findViewById(R.id.avatarProfile));
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
    }

    private void SetupCLickEvent(){
        // EditProfile
        Button editProfile = root.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile_Click(v);
            }
        });
    }

    public void EditProfile_Click(View view){
        Intent editProfile = new Intent(getActivity(),AccountEditProfile.class);
        editProfile.putExtra("userProfile",userInfor);
        getActivity().startActivityForResult(editProfile, MainActivity.RC_EDITPROFILE);
        getActivity().overridePendingTransition(R.anim.from_bottom_up_light,R.anim.to_top_light);
    }

}