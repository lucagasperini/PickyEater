package com.pickyeaters.logic.model;

public class Restaurateur extends User {
    private String ssn; //Sarebbe il codice fiscale
    private String phone;
    private Restaurant restaurant;

    public Restaurateur(
            String id,
            String email,
            String firstname,
            String lastname,
            String phone,
            String ssn,
            Restaurant restaurant) {
        super(id, email, firstname, lastname);
        this.phone = phone;
        this.ssn = ssn;
        this.restaurant = restaurant;
    }

    public Restaurateur(Restaurateur restaurateur) {
        super(restaurateur);
        this.ssn = restaurateur.ssn;
        this.restaurant = new Restaurant(restaurateur.restaurant);
    }

    public String getPhone() {
        return phone;
    }

    public String getSsn() {
        return ssn;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
