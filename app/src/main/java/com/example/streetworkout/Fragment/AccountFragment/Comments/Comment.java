package com.example.streetworkout.Fragment.AccountFragment.Comments;

import com.example.streetworkout.User.UserInfor;

import java.io.Serializable;

public class Comment implements Serializable {
    private String commentId;
    private String uid;
    private String content;
    private String dateCreate;
    public UserInfor userInfor;

    public Comment(){}

    public Comment(String commentId,String uid,String content, String dateCreate){
        this.commentId = commentId;
        this.uid = uid;
        this.content = content;
        this.dateCreate = dateCreate;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
