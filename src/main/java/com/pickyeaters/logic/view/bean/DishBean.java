package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.model.*;

import java.util.LinkedList;

public class DishBean {
    private String id;
    private String name;
    private String description;
    private String category;
    private boolean active;
    private LinkedList<String> ingredientList = new LinkedList<>();

    //TODO: check category?
    public DishBean(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public DishBean(Dish dish) {
        this.id = dish.getID();
        this.name = dish.getName();
        this.description = dish.getDescription();
        this.active = dish.isActive();
        this.category = dish.getType();
        for(Ingredient i : dish.getIngredientList()) {
            ingredientList.add(i.getName());
        }
    }

    public void addIngredient(String name) {
        ingredientList.add(name);
    }

    public String getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public LinkedList<String> getIngredientList() {
        return ingredientList;
    }

    public Dish toDish() {
        return switch (category) {
            case Dish.TYPE_APPETIZER -> new DishAppetizer(name, description);
            case Dish.TYPE_CONTOUR -> new DishContour(name, description);
            case Dish.TYPE_DESSERT -> new DishDessert(name, description);
            case Dish.TYPE_DRINK -> new DishDrink(name, description);
            case Dish.TYPE_SECOND -> new DishSecond(name, description);
            case Dish.TYPE_FIRST -> new DishFirst(name, description);
            default -> throw new IllegalStateException("Unexpected value: " + category);
        };
    }
}
