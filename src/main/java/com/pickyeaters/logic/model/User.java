package com.pickyeaters.logic.model;

import java.util.Date;

public abstract class User {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private Date birthdate;


    public User(String id, String email) {
        this(id, email, null, null);
    }
    public User(String id, String email, String firstname, String lastname) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
