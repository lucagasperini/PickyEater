package com.pickyeaters.logic.view.bean;

public class RestaurateurBean {
    private String firstname;
    private String lastname;
    private String birthDate;
    private String ssn;
    private String restaurantPhone;
    private String email;
    private String restaurantName;
    private String restaurantAddress;

    public RestaurateurBean(
            String email,
            String firstname,
            String lastname,
            String ssn,
            String restaurantName,
            String restaurantPhone,
            String restaurantAddress) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssn = ssn;
        this.restaurantName = restaurantName;
        this.restaurantPhone = restaurantPhone;
        this.restaurantAddress = restaurantAddress;
    }
    public String getFirstname(){ return firstname; }
    public String getLastname(){ return lastname; }
    public String getBirthDate(){ return birthDate; }
    public String getSsn(){ return ssn; }
    public String getRestaurantPhone(){ return restaurantPhone; }
    public String getEmail(){ return email; }

    public String getRestaurantName() {
        return restaurantName;
    }
    public String getRestaurantAddress() {
        return restaurantAddress;
    }
}
