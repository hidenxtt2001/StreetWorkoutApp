package com.example.streetworkout.Fragment.TrainningFragment.Program;


public class Program {
    int image;
    int likes;
    int cmt;
    String kind_WO;
    String name_WO;
    String excercise_lvl;

    public Program()
    {

    }

    public Program(int image, int likes, int cmt, String kind_WO, String name_WO, String excercise_lvl) {
        this.image = image;
        this.likes = likes;
        this.cmt = cmt;
        this.kind_WO = kind_WO;
        this.name_WO = name_WO;
        this.excercise_lvl = excercise_lvl;
    }

    public Program(int image) {
        this.image=image;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getCmt() {
        return cmt;
    }

    public void setCmt(int cmt) {
        this.cmt = cmt;
    }

    public String getKind_WO() {
        return kind_WO;
    }

    public void setKind_WO(String kind_WO) {
        this.kind_WO = kind_WO;
    }

    public String getName_WO() {
        return name_WO;
    }

    public void setName_WO(String name_WO) {
        this.name_WO = name_WO;
    }

    public String getExcercise_lvl() {
        return excercise_lvl;
    }

    public void setExcercise_lvl(String excercise_lvl) {
        this.excercise_lvl = excercise_lvl;
    }
}
