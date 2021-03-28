package com.example.streetworkout.User;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class UserInfor implements Serializable {
    private String uid;
    private String dislayName;
    private String email;
    private String urlAvatar;
    private String birthDay;;

    public UserInfor() {

    }

    public UserInfor(String uid, String dislayName, String email, @NonNull String urlAvatar) {
        this.uid = uid;
        this.dislayName = dislayName;
        this.email = email;
        this.urlAvatar = urlAvatar;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public String getDislayName() {
        return dislayName;
    }

    public String getBirthDay() {
        return birthDay;
    }
}
