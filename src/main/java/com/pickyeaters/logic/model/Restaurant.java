package com.pickyeaters.logic.model;

public class Restaurant {
    private String name;
    private String phone;
    private String address;
    private BusinessHours businessHours;

    public Restaurant(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
