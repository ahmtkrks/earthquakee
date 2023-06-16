package com.example.earthquakee.Model;

public class Users {
    public String name;
    public String email;
    public String uid;
    public String userProfile;
    public String latitude;
    public String longtiude;

    public Users(String name, String email, String uid, String userProfile, String lat, String longtiude) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.userProfile = userProfile;
        this.latitude = lat;
        this.longtiude = longtiude;
    }


    public Users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getLatiude() {
        return latitude;
    }

    public void setLatiude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtiude() {
        return longtiude;
    }

    public void setLongtiude(String longtiude) {
        this.longtiude = longtiude;
    }
}
