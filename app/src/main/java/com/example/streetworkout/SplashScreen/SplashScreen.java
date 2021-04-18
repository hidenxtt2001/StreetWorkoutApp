package com.example.streetworkout.SplashScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.Login.LoginActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler timer = new Handler();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                MainAcitityAccess();
            }
        };
        timer.postDelayed(task,500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    private void MainAcitityAccess(){
        Intent profile = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(profile,0);
        overridePendingTransition(R.anim.from_right_start,R.anim.from_left_end);
    }
}

