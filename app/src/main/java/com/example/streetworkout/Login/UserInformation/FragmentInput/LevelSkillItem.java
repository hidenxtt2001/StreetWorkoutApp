package com.example.streetworkout.Login.UserInformation.FragmentInput;


public class LevelSkillItem {
    private int imageLevel;
    private String nameLevel;

    public LevelSkillItem(int imageLevel, String nameLevel) {
        this.imageLevel = imageLevel;
        this.nameLevel = nameLevel;
    }

    public int getImageLevel() {
        return imageLevel;
    }

    public String getNameLevel() {
        return nameLevel;
    }
}
