package com.example.earthquakee.Model;

public class Users {
    public String name;
    public String email;
    public String uid;

    public String userProfile;

    public Users(String name, String email, String uid, String userProfile) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.userProfile = userProfile;
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
}
