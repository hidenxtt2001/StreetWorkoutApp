package com.example.streetworkout.Fragment.CalenderFragment.WeekExercise;

import java.io.Serializable;

public class WeekExerciseDaily implements Serializable {
    private String idWeekExercise;
    private String idGroupExercise;

    public WeekExerciseDaily() {}

    public WeekExerciseDaily(String idWeekExercise, String idGroupExercise) {
        this.idWeekExercise = idWeekExercise;
        this.idGroupExercise = idGroupExercise;
    }

    public String getIdWeekExercise() {
        return idWeekExercise;
    }

    public String getIdGroupExercise() {
        return idGroupExercise;
    }
}