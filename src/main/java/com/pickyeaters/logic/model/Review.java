package com.pickyeaters.logic.model;

import com.pickyeaters.logic.utils.Grade;

public class Review {
    private Pickie user;
    private Dish dish;
    private Grade grade;

    public Review(Pickie user, Dish dish, Grade grade) {
        setDish(dish);
        setUser(user);
        setGrade(grade);
    }

    public Pickie getUser() {
        return user;
    }

    public Dish getDish() {
        return dish;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setUser(Pickie user) {
        this.user = user;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
