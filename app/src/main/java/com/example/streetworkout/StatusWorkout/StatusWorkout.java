package com.example.streetworkout.StatusWorkout;

import com.example.streetworkout.User.UserInfor;

import java.io.Serializable;

public class StatusWorkout implements Serializable {
    private String idStatus;
    private String uid;
    private String idGroupExercise;
    private String dateComplate;
    private UserInfor userInfor;
    private String timeComplete;


    public StatusWorkout(){}


    public StatusWorkout( String uid, String idGroupExercise, String dateComplate) {
        this.uid = uid;
        this.idGroupExercise = idGroupExercise;
        this.dateComplate = dateComplate;
    }
    public String getIdStatus() { return idStatus; }

    public void setIdStatus( String idStatus){
        this.idStatus = idStatus;
    }

    public String getUid() { return uid; }

    public void setUid( String uid){
        this.uid = uid;
    }

    public String getIdGroupExercise() { return idGroupExercise; }

    public void setIdGroupExercise( String idGroupExercise){
        this.idGroupExercise = idGroupExercise;
    }

    public String getDateComplate() { return dateComplate; }

    public void setDateComplate( String dateComplate) {
        this.dateComplate = dateComplate;
    }

    public UserInfor getUserInfor() {
        return userInfor;
    }

    public void setUserInfor(UserInfor userInfor) {
        this.userInfor = userInfor;
    }

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }
}
