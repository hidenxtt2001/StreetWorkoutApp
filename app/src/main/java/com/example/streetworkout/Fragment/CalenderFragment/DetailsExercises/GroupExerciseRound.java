package com.example.streetworkout.Fragment.CalenderFragment.DetailsExercises;

import java.io.Serializable;

public class GroupExerciseRound implements Serializable {
    private  String idGroupExercise;
    private  String idExercise;

    public GroupExerciseRound(){}

    public GroupExerciseRound(String idGroupExercise, String idExercise) {
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
