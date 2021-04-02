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

import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.squareup.picasso.Picasso;


public class AccountFragment extends Fragment {

    View root;
    final UserInfor userInfor;
    public AccountFragment(UserInfor userInfor) {
        // Required empty public constructor
        this.userInfor = userInfor;
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
        // Set Avatar Profile
        Picasso.get().load(Uri.parse(userInfor.getUrlAvatar())).into((ImageView)root.findViewById(R.id.avatarProfile));
        // Name
        TextView nameDislay = root.findViewById(R.id.nameDislay);
        nameDislay.setText(userInfor.getDislayName());
        // Username
        TextView userName = root.findViewById(R.id.userName);
        userName.setText(userInfor.getUserName());
        // EditProfile
        Button editProfile = root.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile_Click(v);
            }
        });
        return root;
    }

    public void EditProfile_Click(View view){
        Intent editProfile = new Intent(getActivity(),AccountEditProfile.class);
        startActivity(editProfile);
    }
}