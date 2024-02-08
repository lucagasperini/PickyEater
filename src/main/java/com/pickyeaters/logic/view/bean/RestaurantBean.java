package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.model.Restaurant;

public class RestaurantBean {
    private String ID;
    private String name;
    private String address;
    private String phone;

    public RestaurantBean(String ID, String name, String address, String phone) {
        setID(ID);
        setName(name);
        setAddress(address);
        setPhone(phone);
    }

    public RestaurantBean(Restaurant restaurant) {
        this(restaurant.getID(),
            restaurant.getName(),
            restaurant.getAddress(),
            restaurant.getPhone()
        );
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }
}
