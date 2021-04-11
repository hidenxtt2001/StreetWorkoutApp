package com.example.streetworkout.Login.UserInformation.FragmentInput;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.streetworkout.Login.UserInformation.InputUserProfile;
import com.example.streetworkout.R;
import com.example.streetworkout.User.UserInfor;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InputUserInfor extends Fragment {

    View root;
    TextInputEditText userName;
    TextInputEditText birthDay;
    TextInputLayout wrapBirthday;
    TextInputLayout wrapUsername;
    DatePickerDialog datePickerDialog;
    Button nextInput;
    public InputUserInfor() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.login_input_user_fragment_infor, container, false);
        // Setup Element
        userName = root.findViewById(R.id.userName);
        birthDay = root.findViewById(R.id.userBirthday);
        wrapBirthday = root.findViewById(R.id.wrap_userBirthday);
        wrapUsername = root.findViewById(R.id.wrap_userName);
        nextInput = root.findViewById(R.id.nextInfo);


        birthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        nextInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteProfile(v);
            }
        });
        SetupDateDialog();

        return root;
    }

    private void showDateDialog() {
        datePickerDialog.show();
    }

    private void SetupDateDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                birthDay.setText(simpleDateFormat.format(calendar.getTime()));
                birthDay.clearFocus();
            }
        };
        datePickerDialog = new DatePickerDialog(getActivity(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void CompleteProfile(View view) {
        userName.clearFocus();
        birthDay.clearFocus();
        boolean checkValid = true;
        String username = userName.getText().toString();
        if (!username.matches("^[a-zA-Z0-9_]{6,30}$")) {
            checkValid = false;
            if (username.length() < 6)
                wrapUsername.setError("At least 6 characters");
            else
                wrapUsername.setError("Invalid Username");
        }

        if (birthDay.getText().toString().trim().equals("")) {
            checkValid = false;
            wrapBirthday.setError("Invalid Birthday");
        }

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference().child("UserInfos");
        Query checkuserName = reference.orderByChild("userName").equalTo(username);
        boolean finalCheckValid = checkValid;
        checkuserName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    if (finalCheckValid) {
                        InputUserProfile.userInfor.setUserName(username);
                        InputUserProfile.userInfor.setBirthDay(birthDay.getText().toString());
                        ((InputUserProfile)getActivity()).SetupFragment(new InputUserLevel());
                        checkuserName.removeEventListener(this);
                    }
                }
                else
                {
                    wrapUsername.setError("Username already exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}