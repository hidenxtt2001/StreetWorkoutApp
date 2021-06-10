package com.example.streetworkout.Fragment.TrainningFragment.Program;


import java.io.Serializable;

public class ProgramExercise implements Serializable {
    private String idProgramExercise;
    private String nameProgramExercise;
    private String linkImageProgramExercise;
    public  ProgramExercise(){

    }

    public ProgramExercise(String nameProgramExercise) {
        this.nameProgramExercise = nameProgramExercise;
    }

    public String getIdProgramExercise() {
        return idProgramExercise;
    }

    public void setIdProgramExercise(String idProgramExercise) {
        this.idProgramExercise = idProgramExercise;
    }

    public String getNameProgramExercise() {
        return nameProgramExercise;
    }

    public String getLinkImageProgramExercise() {
        return linkImageProgramExercise;
    }

    public void setLinkImageProgramExercise(String linkImageProgramExercise) {
        this.linkImageProgramExercise = linkImageProgramExercise;
    }
}
