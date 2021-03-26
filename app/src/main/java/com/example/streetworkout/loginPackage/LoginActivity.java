package com.example.streetworkout.loginPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.streetworkout.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Setup Intro View
        SetupIntro();

        // Data Setup
        mAuth = FirebaseAuth.getInstance();

        // Setup Google
        SetupGoogleLogin();


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
                            UILoginSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    //endregion

    //region Facebook Login

    public void LoginFacebook_Click(View view) {

    }

    //endregion

    private void UILoginSuccess(){

    }
}