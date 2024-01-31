package com.pickyeaters.logic.model;

public class Administrator extends User {
    public Administrator(String id, String username, String firstname, String lastname) {
        super(id, username, firstname, lastname);
    }

    public Administrator(Administrator administrator) {
        super(administrator);
    }
    public Administrator(String id, String username) {
        super(id, username);
    }
}
