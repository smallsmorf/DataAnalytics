package com.models;

public class User {
    private String username;
    private String firstname;

    private String lastname;

    private String password;
    private boolean isVip;

    public User(String username, String firstname, String lastname, String password, boolean isVip) {
        this.username = username;
        this.firstname = firstname;
        this.lastname= lastname;
        this.password = password;
        this.isVip = isVip;
    }

    public String getUsername() {
        return username;
    }
    public String getFullname() {
        return firstname + " " + lastname;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }
}
