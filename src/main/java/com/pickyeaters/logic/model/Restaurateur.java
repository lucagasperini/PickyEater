package com.pickyeaters.logic.model;

public class Restaurateur extends User {
    private String socialSecurityNumber; //Sarebbe il codice fiscale
    private Restaurant restaurant;

    public Restaurateur(String username, Restaurant restaurant) {
        super(username);
        this.restaurant = restaurant;
    }
    public Restaurateur(String username, String firstname, String lastname, Restaurant restaurant) {
        super(username, firstname, lastname);
        this.restaurant = restaurant;
    }
}
