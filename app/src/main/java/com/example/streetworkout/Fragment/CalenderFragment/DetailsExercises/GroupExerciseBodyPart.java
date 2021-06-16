package com.example.streetworkout.Fragment.CalenderFragment.DetailsExercises;


import java.io.Serializable;

public class GroupExerciseBodyPart implements Serializable {
    private String idGroupExercise;
    private int bodyValue;

    public GroupExerciseBodyPart(){}

    public GroupExerciseBodyPart(String idGroupExercise, int bodyValue) {
        this.idGroupExercise = idGroupExercise;
        this.bodyValue = bodyValue;
    }

    public String getIdGroupExercise() {
        return idGroupExercise;
    }

    public int getBodyValue() {
        return bodyValue;
    }
}