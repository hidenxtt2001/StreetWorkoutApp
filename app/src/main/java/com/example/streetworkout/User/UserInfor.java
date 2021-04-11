package com.example.streetworkout.User;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.Date;

public class UserInfor implements Serializable {
    private String uid;
    private String displayName;
    private String userName;
    private String email;
    private String urlAvatar;
    private Status status;
    private String birthDay;
    private int experienceLevel;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Status implements Serializable {
        private int workoutStatus = 0;
        private int followersStatus = 0;
        private int followingStatus = 0;

        public Status() {
        }

        public int getWorkoutStatus() {
            return workoutStatus;
        }

        public void setWorkoutStatus(int workoutStatus) {
            this.workoutStatus = workoutStatus;
        }

        public int getFollowersStatus() {
            return followersStatus;
        }

        public void setFollowersStatus(int followersStatus) {
            this.followersStatus = followersStatus;
        }

        public int getFollowingStatus() {
            return followingStatus;
        }

        public void setFollowingStatus(int followingStatus) {
            this.followingStatus = followingStatus;
        }
    }

    public UserInfor() {

    }

    public UserInfor(String uid) {
        this.uid = uid;
        this.status = new Status();
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
}
