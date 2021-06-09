package com.example.streetworkout.Fragment.CalenderFragment.DetailsExercises;

import com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises.GroupExercise;

import java.io.Serializable;

public class GroupExerciseWarmup implements Serializable {
    private  String idGroupExercise;
    private  String idExercise;

    public GroupExerciseWarmup(){}

    public GroupExerciseWarmup(String idGroupExercise, String idExercise) {
        this.idGroupExercise = idGroupExercise;
        this.idExercise = idExercise;
    }

    public String getIdGroupExercise() {
        return idGroupExercise;
    }

    public String getIdExercise() {
        return idExercise;
    }
}
