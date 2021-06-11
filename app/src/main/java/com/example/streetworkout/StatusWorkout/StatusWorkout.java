package com.example.streetworkout.StatusWorkout;

import com.example.streetworkout.User.UserInfor;

public class StatusWorkout {
    private String idStatus;
    private String uid;
    private String idGroupExercise;
    private String dateComplate;
    private UserInfor userInfor;


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
        this.idGroupExercise = idGroupExercise;
    }

    public UserInfor getUserInfor() {
        return userInfor;
    }

    public void setUserInfor(UserInfor userInfor) {
        this.userInfor = userInfor;
    }
}
