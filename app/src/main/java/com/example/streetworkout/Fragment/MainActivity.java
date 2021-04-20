package com.example.streetworkout.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.streetworkout.Fragment.AccountFragment.AccountFragment;
import com.example.streetworkout.Fragment.CalenderFragment.CalenderFragment;
import com.example.streetworkout.Fragment.NewsFeedFragment.NewsFeedFragment;
import com.example.streetworkout.Fragment.TrainningFragment.TrainingFragment;
import com.example.streetworkout.Login.LoginActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import static com.example.streetworkout.R.id;
import static com.example.streetworkout.R.layout;

public class MainActivity extends AppCompatActivity {

    public static UserInfor userInfor;
    private static FirebaseAuth mAuth;
    // Value Intent
    public final static int RC_LOGINACCOUNT = 12412;
    public final static int RC_EDITPROFILE = 45352;
    // Value Result
    public final static int RESULT_LOGOUT = 94733;
    public final static int RESULT_SAVEPROFILE = 83944;
    // Save Data User
    SharedPreferences UserData;
    SharedPreferences.Editor EditUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Login Status
        mAuth = FirebaseAuth.getInstance();
        UserData = getPreferences(MODE_PRIVATE);
        EditUserData = UserData.edit();
        EditUserData.apply();
        if(!UserData.getBoolean("alreadyLogin",false)){
            LoginAccount();
        }
        else {
            Gson gson = new Gson();
            userInfor = gson.fromJson(UserData.getString("userProfile", null),UserInfor.class);
        }

        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        // Set up bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(id.main_bottom_navigation);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new TrainingFragment()).commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case id.navigation_item_newsFeed:
                    selectedFragment = new NewsFeedFragment();

                    break;
                case id.navigation_item_training:
                    selectedFragment = new TrainingFragment();
                    break;
                case id.navigation_item_calender:
                    selectedFragment = new CalenderFragment();
                    break;
                case id.navigation_item_account:
                    AccountFragment.userInfor = userInfor;
                    selectedFragment = new AccountFragment();
                    break;
            }

            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, selectedFragment).commit();

            return true;
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RC_LOGINACCOUNT:
                if(resultCode != RESULT_OK){
                    finish();
                }
                else {
                    userInfor = (UserInfor) data.getExtras().getSerializable("userProfile");
                    EditUserData.putString("userProfile",new Gson().toJson(userInfor));
                    EditUserData.putBoolean("alreadyLogin",true);
                    EditUserData.apply();
                }
            case RC_EDITPROFILE:
                if(resultCode == RESULT_LOGOUT){
                    EditUserData.putString("userProfile",null);
                    EditUserData.putBoolean("alreadyLogin",false);
                    EditUserData.apply();
                    LoginAccount();
                }
                else if (resultCode == RESULT_SAVEPROFILE){
                    UserInfor temp = (UserInfor) data.getExtras().getSerializable("userProfile");
                    if(UpdateNewAccount(temp)){
                        userInfor = temp;
                        EditUserData.putString("userProfile",new Gson().toJson(userInfor));
                        EditUserData.apply();
                        AccountFragment.userInfor = userInfor;
                    }
                    else {
                        //TODO: Show message Error Update Data Account Profile
                    }
                }
        }
    }

    private boolean UpdateNewAccount(UserInfor temp){
        if(isNetworkAvailable(getApplicationContext())) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference().child("UserInfos");
            reference.child(userInfor.getUid()).setValue(temp);
            return true;
        }
        return false;
    }

    private void LoginAccount(){
        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(login,RC_LOGINACCOUNT);
    }

    public static boolean isNetworkAvailable(Context con) {
        try {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}