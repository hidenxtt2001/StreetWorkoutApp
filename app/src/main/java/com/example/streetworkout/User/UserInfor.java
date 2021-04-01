package com.example.streetworkout.User;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class UserInfor implements Serializable {
    private String uid;
    private String dislayName;
    private String userName;
    private String email;
    private String urlAvatar;
    private String birthDay;;

    public UserInfor() {

    }

    public UserInfor(String uid) {
        this.uid = uid;
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

    public void setDislayName(String dislayName) {
        this.dislayName = dislayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}
