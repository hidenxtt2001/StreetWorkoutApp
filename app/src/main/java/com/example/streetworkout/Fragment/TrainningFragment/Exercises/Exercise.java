package com.example.streetworkout.Fragment.TrainningFragment.Exercises;

import java.io.Serializable;

public class Exercise implements Serializable {
    private  String idExercise;
    private String nameExercise;
    private String linkVideo;
    private String linkImage;

    public Exercise(){

    }
    public Exercise(String nameExercise) {
        this.nameExercise = nameExercise;
        this.linkVideo = linkVideo;
    }


    public String getNameExercise() {
        return nameExercise;
    }

    public void setNameExercise(String nameExercise) {
        this.nameExercise = nameExercise;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }



    public String getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(String idExercise) {
        this.idExercise = idExercise;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
