package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;

public class DishBean {
    private String id;
    private String name;
    private String description;
    private String category;
    public DishBean(String id, String name) throws BeanException {
        setID(id);
        setName(name);
    }
    public DishBean(String id, String name, String description, String category) throws BeanException {
        setID(id);
        setName(name);
        setDescription(description);
        setCategory(category);
    }

    public DishBean(String name, String description, String category) throws BeanException {
        this("", name, description, category);
    }

    public String getID() {
        return id;
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

    public void setID(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
