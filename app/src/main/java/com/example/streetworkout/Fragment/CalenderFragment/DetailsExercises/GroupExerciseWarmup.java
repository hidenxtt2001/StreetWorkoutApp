package com.example.streetworkout.Fragment.CalenderFragment.DetailsExercises;

import java.io.Serializable;

public class GroupExerciseWarmup implements Serializable {
    private final String idGroupExercise;
    private final String idExercise;

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
