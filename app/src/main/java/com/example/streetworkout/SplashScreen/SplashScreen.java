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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Handler timer = new Handler();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    UILoginSuccess(user);
                }
                else {
                    GotoLogin();
                }
            }
        };
        timer.postDelayed(task,1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            finish();
        }
    }

    private void GotoLogin() {
        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(login,0);
        overridePendingTransition(R.anim.from_right_start,R.anim.from_left_end);
    }

    private void UILoginSuccess(FirebaseUser user){

        final boolean[] checkLoginSuceess = {false};
        final boolean[] timeOut = {false};
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference().child("UserInfos").child(user.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkLoginSuceess[0] = true;
                if(timeOut[0]) return;
                if(snapshot.exists()){
                    SetProfile(snapshot.getValue(UserInfor.class));
                }
                else {
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Handler handler = new Handler();

        TimerTask checkTimeout = new TimerTask() {
            @Override
            public void run() {
                timeOut[0] = true;
                if(!checkLoginSuceess[0]){
                    ShowToast("Network not connection");
                    GotoLogin();
                }
            }
        };
        handler.postDelayed(checkTimeout,20000);
    }
    private void ShowToast(String message){
        View customLayout = LayoutInflater.from(this).inflate(R.layout.toast_custom,(ViewGroup) findViewById(R.id.toastGroup));
        TextView content = customLayout.findViewById(R.id.toastMessage);
        content.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setView(customLayout);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    private void SetProfile(UserInfor userInfor){
        Intent profile = new Intent(getApplicationContext(), MainActivity.class);
        profile.putExtra("userProfile",userInfor);
        startActivityForResult(profile,0);
        overridePendingTransition(R.anim.from_right_start,R.anim.from_left_end);
    }
}

