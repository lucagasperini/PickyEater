package com.pickyeaters.logic.view;

import com.pickyeaters.logic.view.bean.UserBean;

public class AppData {
    private final static AppData instance = new AppData();
    public static AppData getInstance() {
        return instance;
    }

    private AppData(){}

    private UserBean user;

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getUserID() {
        return user.getID();
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    public UserBean.UserType getUserType() {
        return user.getType();
    }

    public String getUserName() {
        return user.getName();
    }

    public String getRestaurantID() {
        return user.getRestaurant().getID();
    }
}
