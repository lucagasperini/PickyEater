package com.pickyeaters.logic.model;

public class Pickie extends User{
    String username;
    public Pickie(String id, String email, String username, String firstname, String lastname) {
        super(id, email, firstname, lastname);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
