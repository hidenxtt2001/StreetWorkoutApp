package com.example.streetworkout.Login.UserInformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Login.UserInformation.FragmentInput.InputUserInfor;
import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import javax.xml.transform.Result;

import de.hdodenhof.circleimageview.CircleImageView;

public class InputUserProfile extends AppCompatActivity {
    public static UserInfor userInfor;

    // Element
    TextView nameDisplay;
    CircleImageView userAvatar;
    private int ResultCallBack = RESULT_CANCELED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_input_user_profile);


        // Setup Defult Profile
        userInfor = (UserInfor) this.getIntent().getExtras().getSerializable("userProfile");

        // Toolbar
        Toolbar toolbarInput = findViewById(R.id.toolbarInput);
        toolbarInput.setTitle("");
        setSupportActionBar(toolbarInput);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SetupFragment(new InputUserInfor());
        // Setup User
        SetupElement();

        SetupUserInfo();


    }

    public void SetupFragment(Fragment fragment){
        // Create new fragment and transaction
        if(fragment instanceof InputUserInfor){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_create_account, fragment).commit();
        }
        else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container_create_account, fragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
//
    }



    private void SetupElement() {
        nameDisplay = findViewById(R.id.nameDislay);
        userAvatar = findViewById(R.id.userAvatar);
    }

    private void SetupUserInfo() {

        nameDisplay.setText(userInfor.getDisplayName());
        Glide.with(this.getApplicationContext()).load(Uri.parse(userInfor.getUrlAvatar())).into(userAvatar);

        //userName.setText(userInfor.getEmail().split("@")[0]);
    }

    // Update Data User To Firebase
    public void PutDataUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("UserInfos");
        reference.child(userInfor.getUid()).setValue(userInfor);
        ResultCallBack = RESULT_OK;
        setResult(ResultCallBack);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(ResultCallBack);
        super.onBackPressed();
        overridePendingTransition(R.anim.to_down_light, R.anim.from_bottom_down_light);
    }

}