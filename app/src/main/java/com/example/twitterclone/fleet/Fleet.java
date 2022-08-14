package com.example.twitterclone.fleet;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Fleet {

    public Drawable userProfile;
    public List<Drawable> fleetImages;
    public String userName;

    public Fleet(Drawable userProfile, List<Drawable> fleetImages, String userName) {
        this.userProfile = userProfile;
        this.fleetImages = fleetImages;
        this.userName = userName;
    }

    public Fleet() {
    }

    public Drawable getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Drawable userProfile) {
        this.userProfile = userProfile;
    }

    public List<Drawable> getFleetImages() {
        return fleetImages;
    }

    public void setFleetImages(List<Drawable> fleetImages) {
        this.fleetImages = fleetImages;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
