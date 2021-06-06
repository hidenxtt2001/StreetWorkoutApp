package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import java.io.Serializable;

public class GroupExercise  implements Serializable {
    private String idGroupExercise;
    private String linkImageGroup;
    private String nameGroupExercise;

    public GroupExercise(){}

    public String getIdGroupExercise() {
        return idGroupExercise;
    }

    public void setIdGroupExercise(String idGroupExercise) {
        this.idGroupExercise = idGroupExercise;
    }

    public String getLinkImageGroup() {
        return linkImageGroup;
    }

    public void setLinkImageGroup(String linkImageGroup) {
        this.linkImageGroup = linkImageGroup;
    }

    public String getNameGroupExercise() {
        return nameGroupExercise;
    }

    public void setNameGroupExercise(String nameGroupExercise) {
        this.nameGroupExercise = nameGroupExercise;
    }
}
