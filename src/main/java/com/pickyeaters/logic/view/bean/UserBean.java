package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.model.User;

public class UserBean {
    private String name;
    private String email;

    public UserBean(User user) {
        this.name = user.getFirstname() + " " + user.getLastname();
        this.email = user.getEmail();
    }
    public UserBean(String email, String name) {
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
