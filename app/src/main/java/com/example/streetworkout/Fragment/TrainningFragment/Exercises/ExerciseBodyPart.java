package com.example.streetworkout.Fragment.TrainningFragment.Exercises;

public class ExerciseBodyPart {
    private String idExercise;
    private int bodyValue;

    public ExerciseBodyPart(){

    }
    public ExerciseBodyPart(String idExercise, int bodyValue) {
        this.idExercise = idExercise;
        this.bodyValue = bodyValue;
    }

    public String getIdExercise() {
        return idExercise;
    }

    public int getBodyValue() {
        return bodyValue;
    }
}
