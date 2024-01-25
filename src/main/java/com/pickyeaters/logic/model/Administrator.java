package com.pickyeaters.logic.model;

public class Administrator extends User {
    public Administrator(String _id, String _username, String _firstname, String _lastname) {
        super(_id, _username, _firstname, _lastname);
    }
    public Administrator(String _id, String _username) {
        super(_id, _username);
    }
}
