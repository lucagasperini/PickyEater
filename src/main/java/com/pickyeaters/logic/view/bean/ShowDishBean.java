package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;

import java.util.LinkedList;
import java.util.List;

public class ShowDishBean extends DishBean{
    boolean active;
    private List<String> ingredientList = new LinkedList<>();

    public ShowDishBean(String name, String description, String category, boolean active) throws BeanException {
        super(name, description, category);
        setActive(active);
    }

    public List<String> getIngredientList() {
        return ingredientList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
