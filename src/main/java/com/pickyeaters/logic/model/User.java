package com.pickyeaters.logic.model;

import java.util.Date;

public abstract class User {
    private String username; //TODO: toglilo da qui, lo username lo ha solo il Pickie
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String password;
    private Date birthdate;


    public User(String username) {
        this.username = username;
    }
    public User(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

}
