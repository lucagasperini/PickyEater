package com.pickyeaters.app.model;

public abstract class User {
    private String username;
    private String firstname;
    private String lastname;

    public User(String username) {
        this.username = username;
    }
    public User(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

}
