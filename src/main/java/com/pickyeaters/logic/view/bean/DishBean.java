package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;

public class DishBean {
    private String name;
    private String description;
    private String category;
    public DishBean(String name) throws BeanException {
        setName(name);
    }
    public DishBean(String name, String description, String category) throws BeanException {
        setName(name);
        setDescription(description);
        setCategory(category);
    }


    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws BeanException {
        if (name.isEmpty()) {
            throw new BeanException("DISH_NAME_EMPTY", "Dish cannot be empty");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
