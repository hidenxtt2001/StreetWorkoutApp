package com.example.streetworkout.Fragment.AccountFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AccountEditProfile extends AppCompatActivity {

    static UserInfor userInfor;
    EditText yourname, username, email, birthday, country;
    Spinner spinnerGender;
    CountryCodePicker cpp;
    DatePickerDialog datePickerDialog;

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
        cpp = findViewById(R.id.cpp);
        spinnerGender = findViewById(R.id.gender_spinner);
        SetupInfor();
        getNameCountry();

    }

    private void SetupInfor(){
        Picasso.get().load(Uri.parse(userInfor.getUrlAvatar())).into((ImageView)findViewById(R.id.avatarProfile));
        String user_yourname = userInfor.getDisplayName();
        String user_username = userInfor.getUserName();
        String user_email = userInfor.getEmail();
        String user_birthday = userInfor.getBirthDay();

        yourname.setText(user_yourname);
        username.setText(user_username);
        email.setText(user_email);
        birthday.setText(user_birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        //gender.setText(user_gender);
        cpp.setCountryForNameCode(userInfor.getCountry());
        SetupCalendar();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AccountEditProfile.this
        , R.layout.main_fragment_account_editprofile_spinner_text_style, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(myAdapter);
        spinnerGender.setSelection(userInfor.getGender().equals("male") ? 0 : 1);
    }

    private void getNameCountry() {

        country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }
            }
        });

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

    private void SetupCalendar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(birthday.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                birthday.setText(simpleDateFormat.format(calendar.getTime()));
                birthday.clearFocus();
            }
        };

        datePickerDialog = new DatePickerDialog(this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
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

    public void SaveProfile_Click(View view) {
        userInfor.setDisplayName(yourname.getText().toString());
        userInfor.setBirthDay(birthday.getText().toString());
        userInfor.setCountry(cpp.getSelectedCountryNameCode());
        userInfor.setGender(spinnerGender.getSelectedItemPosition() == 0 ? "male" : "female");
        Intent saveProfile = new Intent();
        saveProfile.putExtra("userProfile",userInfor);
        setResult(MainActivity.RESULT_SAVEPROFILE,saveProfile);
        onBackPressed();
    }

    public void SignOut_Click(View view) {
        setResult(MainActivity.RESULT_LOGOUT);
        onBackPressed();
    }
}