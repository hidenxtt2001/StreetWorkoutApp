package com.example.streetworkout.Fragment.CalenderFragment.WeekExercise;

import java.io.Serializable;

public class WeekExercise implements Serializable {
    private String idWeekExercise;
    private String nameWeekExercise;
    private String imageLinkWeekExercise;
    public WeekExercise(){}

    public WeekExercise(String nameWeekExercise) {
        this.nameWeekExercise = nameWeekExercise;
    }

    public String getNameWeekExercise() {
        return nameWeekExercise;
    }

    public String getIdWeekExercise() {
        return idWeekExercise;
    }

    public void setIdWeekExercise(String idWeekExercise) {
        this.idWeekExercise = idWeekExercise;
    }

    public String getImageLinkWeekExercise() {
        return imageLinkWeekExercise;
    }

    public void setImageLinkWeekExercise(String imageLinkWeekExercise) {
        this.imageLinkWeekExercise = imageLinkWeekExercise;
    }
}
