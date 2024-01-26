package com.pickyeaters.logic.model;

public class Restaurateur extends User {
    private String ssn; //Sarebbe il codice fiscale
    private Restaurant restaurant;

    public Restaurateur(String id, String email, String firstname, String lastname, String ssn, Restaurant restaurant) {
        super(id, email, firstname, lastname);
        this.ssn = ssn;
        this.restaurant = restaurant;
    }

    public String getSsn() {
        return ssn;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
