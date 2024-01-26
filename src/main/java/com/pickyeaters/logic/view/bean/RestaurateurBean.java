package com.pickyeaters.logic.view.bean;

public class RestaurateurBean {
    private String name;
    private String surname;
    private String birthDate;
    private String ssn;
    private String phoneNumber;
    private String emailAddress;

    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getName(){ return name; }
    public String getSurname(){ return surname; }
    public String getBirthDate(){ return birthDate; }
    public String getSsn(){ return ssn; }
    public String getPhoneNumber(){ return phoneNumber; }
    public String getEmailAddress(){ return emailAddress; }
}
