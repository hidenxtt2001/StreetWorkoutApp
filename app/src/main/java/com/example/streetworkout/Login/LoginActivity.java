package com.example.streetworkout.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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
import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    // Intro
    ViewPager2 introView;
    DotsIndicator dotsIndicator;
    int [] images = {R.drawable.wolf_icon_white,R.drawable.wolf_icon_black,R.drawable.wolf_icon_white};
    IntroAdapter introAdapter;

    // Data User
    private FirebaseAuth mAuth;

    // Google Value
    private final static int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;

    //Facebook Value
    private CallbackManager mCallbackManager;

    // Waiting for Loading Data
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Setup Intro View
        SetupIntro();

        // Setup CustomProgress
        customProgressDialog = new CustomProgressDialog(this);

        // Data Setup
        mAuth = FirebaseAuth.getInstance();

        // Setup Google
        SetupGoogleLogin();

        // Setup Facebook
        SetupFacebookLogin();

        // Already Login
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            UILoginSuccess(user);
        }

    }

    void SetupIntro(){
        // Initialize Viewpager2
        introView = findViewById(R.id.introScroll);
        // Indicate DotIndicate
        dotsIndicator = findViewById(R.id.dotChangeIntro);

        introAdapter = new IntroAdapter(images);

        introView.setAdapter(introAdapter);

        dotsIndicator.setViewPager2(introView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.e("My App",e.getMessage());
            }
        }
        else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }



    }

    //region Google Login

    public void LoginGmail_Click(View view) {
        mGoogleSignInClient.signOut();
        mGoogleSignInClient.revokeAccess();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void SetupGoogleLogin(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UILoginSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    //endregion

    //region Facebook Login



    public void LoginFacebook_Click(View view) {
        LoginButton loginButton =findViewById(R.id.loginFacebookReal);
        loginButton.performClick();
    }

    private void SetupFacebookLogin(){
        mCallbackManager = CallbackManager.Factory.create();
        // Initialize Facebook Login button
        LoginButton loginButton = findViewById(R.id.loginFacebookReal);

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                // ...
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UILoginSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    //endregion

    private void UILoginSuccess(FirebaseUser user){

        customProgressDialog.show();
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
                    UserInfor userInfor = new UserInfor(user.getUid());
                    userInfor.setDisplayName(user.getDisplayName());
                    userInfor.setEmail(user.getEmail());
                    userInfor.setUserName(user.getEmail().split("@")[0]);
                    userInfor.setUrlAvatar(user.getPhotoUrl().toString());
                    reference.setValue(userInfor);
                    SetProfile(userInfor);
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

                    customProgressDialog.dismiss();
                    ShowToast("Network not connection");
                }
            }
        };
        handler.postDelayed(checkTimeout,50000);


    }

    private void SetProfile(UserInfor userInfor){
       Intent profile = new Intent(getApplicationContext(), MainActivity.class);
       profile.putExtra("userProfile",userInfor);
       profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
       profile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
       startActivity(profile);
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
}