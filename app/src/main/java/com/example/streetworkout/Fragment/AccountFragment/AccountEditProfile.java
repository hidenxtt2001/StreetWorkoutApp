package com.example.streetworkout.Fragment.AccountFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.streetworkout.R;

public class AccountEditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_account_editprofile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing,R.anim.bottom_down);
    }
}