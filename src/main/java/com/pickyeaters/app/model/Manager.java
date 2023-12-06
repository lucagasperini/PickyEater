package com.pickyeaters.app.model;

public class Manager extends User {
    private Restaurant owner;
    public Manager(String username, Restaurant owner) {
        super(username);
        this.owner = owner;
    }
    public Manager(String username, String firstname, String lastname, Restaurant owner) {
        super(username, firstname, lastname);
        this.owner = owner;
    }
}
