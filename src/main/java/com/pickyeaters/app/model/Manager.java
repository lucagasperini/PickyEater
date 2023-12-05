package com.pickyeaters.app.model;

public class Manager extends User {
    private Restaurant owner;
    public Manager(int id, String username, String password, Restaurant owner) {
        super(id, username, password);
        this.owner = owner;
    }
}
