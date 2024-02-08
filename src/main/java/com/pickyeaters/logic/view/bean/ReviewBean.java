package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.utils.Grade;

public class ReviewBean {
    private String dishID;
    private Grade grade;

    public ReviewBean(String dishID, Grade grade) {
        setDishID(dishID);
        setGrade(grade);
    }

    public Grade getGrade() {
        return grade;
    }
    public String getDishID() {
        return dishID;
    }
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public void setDishID(String dishID) {
        this.dishID = dishID;
    }
}
