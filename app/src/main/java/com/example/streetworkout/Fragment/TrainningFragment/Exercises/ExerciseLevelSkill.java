package com.example.streetworkout.Fragment.TrainningFragment.Exercises;

public class ExerciseLevelSkill {
    private String idExercise;
    private int levelValue;

    public ExerciseLevelSkill(){

    }

    public ExerciseLevelSkill(String idExercise, int levelValue) {
        this.idExercise = idExercise;
        this.levelValue = levelValue;
    }

    public String getIdExercise() {
        return idExercise;
    }

    public int getLevelValue() {
        return levelValue;
    }
}
