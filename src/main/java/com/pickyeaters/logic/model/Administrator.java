package com.pickyeaters.logic.model;

public class Administrator extends User {
    public Administrator(String username, String firstname, String lastname) {
        super(username, firstname, lastname);
    }
    public Administrator(String username) {
        super(username);
    }
}
