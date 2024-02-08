package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.utils.Grade;

public class ReviewBean {
    private String dishName;
    private String restaurantID;
    private Grade grade;

    public ReviewBean(String dishName, String restaurantID, Grade grade) {
        setDishName(dishName);
        setRestaurantID(restaurantID);
        setGrade(grade);
    }

    public Grade getGrade() {
        return grade;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}
