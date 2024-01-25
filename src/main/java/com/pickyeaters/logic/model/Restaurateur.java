package com.pickyeaters.logic.model;

public class Restaurateur extends User {
    private String socialSecurityNumber; //Sarebbe il codice fiscale
    private Restaurant restaurant;

    public Restaurateur(String _id, String _username, Restaurant _restaurant) {
        super(_id, _username);
        restaurant = _restaurant;
    }
    public Restaurateur(String _id, String _username, String _firstname, String _lastname, Restaurant _restaurant) {
        super(_id, _username, _firstname, _lastname);
        restaurant = _restaurant;
    }
}
