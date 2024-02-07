package com.pickyeaters.logic.model;

public class Restaurant {
    private String id;
    private String name;
    private String phone;
    private String address;
    private City city;
    private BusinessHours businessHours;

    public Restaurant(String id, String name, String phone, String address, City city) {
        setID(id);
        setName(name);
        setPhone(phone);
        setAddress(address);
        setCity(city);
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public City getCity() {
        return city;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
