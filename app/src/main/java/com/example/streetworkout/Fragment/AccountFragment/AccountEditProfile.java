package com.example.streetworkout.Fragment.AccountFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;

import java.util.Objects;

public class AccountEditProfile extends AppCompatActivity {

    static UserInfor userInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_account_editprofile);
        // User Set Data
        userInfor = (UserInfor) this.getIntent().getExtras().getSerializable("userProfile");
        // Toolbar
        Toolbar toolbar = findViewById(R.id.actionCustomBar);
        toolbar.setTitle("");
        TextView userName = toolbar.findViewById(R.id.userName);
        userName.setText(userInfor.getUserName());
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SetupInfor();
    }

    private void SetupInfor(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.to_down_light,R.anim.from_bottom_down_light);
    }
}