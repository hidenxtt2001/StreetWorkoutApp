package com.example.streetworkout.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.streetworkout.R.*;

public class MainActivity extends AppCompatActivity {

    private static UserInfor userInfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        // Set user profile main
        userInfor = (UserInfor) this.getIntent().getExtras().getSerializable("userProfile");

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
                    selectedFragment = new AccountFragment();
                    break;
            }

            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, selectedFragment).commit();

            return true;
        }

    };

}