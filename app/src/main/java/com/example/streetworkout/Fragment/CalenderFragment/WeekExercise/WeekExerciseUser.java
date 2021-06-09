package com.example.streetworkout.Fragment.CalenderFragment.WeekExercise;

import java.io.Serializable;

public class WeekExerciseUser implements Serializable {
    private String idWeekExercise;
    private boolean day1 = false;
    private boolean day2 = false;
    private boolean day3 = false;
    private boolean day4 = false;
    private boolean day5 = false;
    private boolean day6 = false;
    private boolean day7 = false;

    public WeekExerciseUser(){

    }

    public WeekExerciseUser(String idWeekExercise) {
        this.idWeekExercise = idWeekExercise;
    }

    public String getIdWeekExercise() {
        return idWeekExercise;
    }

    public void setIdWeekExercise(String idWeekExercise) {
        this.idWeekExercise = idWeekExercise;
    }

    public boolean isDay1() {
        return day1;
    }

    public void setDay1(boolean day1) {
        this.day1 = day1;
    }

    public boolean isDay2() {
        return day2;
    }

    public void setDay2(boolean day2) {
        this.day2 = day2;
    }

    public boolean isDay3() {
        return day3;
    }

    public void setDay3(boolean day3) {
        this.day3 = day3;
    }

    public boolean isDay4() {
        return day4;
    }

    public void setDay4(boolean day4) {
        this.day4 = day4;
    }

    public boolean isDay5() {
        return day5;
    }

    public void setDay5(boolean day5) {
        this.day5 = day5;
    }

    public boolean isDay6() {
        return day6;
    }

    public void setDay6(boolean day6) {
        this.day6 = day6;
    }

    public boolean isDay7() {
        return day7;
    }

    public void setDay7(boolean day7) {
        this.day7 = day7;
    }
}
