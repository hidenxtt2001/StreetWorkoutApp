package com.example.streetworkout.Fragment.CalenderFragment;

public class ExercisesEachDay {
    public int resourceImg;
    public String nameDescribe;
    public String nameExercise;

    public ExercisesEachDay(int resourceImg, String nameDescribe, String nameExercise) {
        this.resourceImg = resourceImg;
        this.nameDescribe = nameDescribe;
        this.nameExercise = nameExercise;
    }

    public int getResourceImg() {
        return resourceImg;
    }

    public void setResourceImg(int resourceImg) {
        this.resourceImg = resourceImg;
    }

    public String getNameDescribe() {
        return nameDescribe;
    }

    public void setNameDescribe(String nameDescribe) {
        this.nameDescribe = nameDescribe;
    }

    public String getNameExercise() {
        return nameExercise;
    }

    public void setNameExercise(String nameExercise) {
        this.nameExercise = nameExercise;
    }
}
