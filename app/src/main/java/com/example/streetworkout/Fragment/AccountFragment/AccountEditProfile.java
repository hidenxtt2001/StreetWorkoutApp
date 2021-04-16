package com.example.streetworkout.Fragment.AccountFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AccountEditProfile extends AppCompatActivity {

    static UserInfor userInfor;
    EditText yourname, username, email, birthday, country, gender;
    private CountryCodePicker cpp;

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




        yourname = findViewById(R.id.yourname_edit);
        username = findViewById(R.id.username_edit);
        email = findViewById(R.id.email_edit);
        birthday = findViewById(R.id.birthday_edit);
        country = findViewById(R.id.country_edit);
        gender = findViewById(R.id.gender_edit);
        cpp = findViewById(R.id.cpp);
        SetupInfor();
        getNameCountry();

    }
    private void getNameCountry() {
        cpp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                String nameCountry = cpp.getSelectedCountryName();
                country.setText(nameCountry);
            }
        });
        String nameCountry = cpp.getSelectedCountryName();
        country.setText(nameCountry);
    }

    private void SetupInfor(){

        Picasso.get().load(Uri.parse(userInfor.getUrlAvatar())).placeholder(R.drawable.menu_account_fragment_avatar).error(R.drawable.menu_account_fragment_avatar).into((ImageView)findViewById(R.id.avatarProfile));
        String user_yourname = userInfor.getDisplayName();
        String user_username = userInfor.getUserName();
        String user_email = userInfor.getEmail();
        String user_birthday = userInfor.getBirthDay();
        String user_gender = userInfor.getGender();

        yourname.setText(user_yourname);
        username.setText(user_username);
        email.setText(user_email);
        birthday.setText(user_birthday);
        gender.setText(user_gender);
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