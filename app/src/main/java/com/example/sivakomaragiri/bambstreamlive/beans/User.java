package com.example.sivakomaragiri.bambstreamlive.beans;

import android.util.Log;

public class User {
    String userMail;
    String userid;
    Boolean live;
    Long lastEdited;

    public Long getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Long lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Boolean isLive() {
        return live;
    }


    public void setLive(Boolean live) {
        this.live = live;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
