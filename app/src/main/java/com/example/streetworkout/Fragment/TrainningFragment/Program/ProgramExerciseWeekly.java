package com.example.streetworkout.Fragment.TrainningFragment.Program;

import java.io.Serializable;

public class ProgramExerciseWeekly implements Serializable {
    private String idProgramExercise;
    private String idWeekExercise;

    public ProgramExerciseWeekly(){

    }

    public ProgramExerciseWeekly(String idProgramExercise, String idWeekExercise) {
        this.idProgramExercise = idProgramExercise;
        this.idWeekExercise = idWeekExercise;
    }

    public String getIdProgramExercise() {
        return idProgramExercise;
    }

    public String getIdWeekExercise() {
        return idWeekExercise;
    }
}
