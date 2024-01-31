package com.pickyeaters.logic.model;

import java.util.LinkedList;
import java.util.List;

public abstract class Dish {
    public static final String TYPE_APPETIZER = "APPETIZER";
    public static final String TYPE_DRINK = "DRINK";
    public static final String TYPE_FIRST = "FIRST";
    public static final String TYPE_SECOND = "SECOND";
    public static final String TYPE_CONTOUR = "CONTOUR";
    public static final String TYPE_DESSERT = "DESSERT";

    private String id;
    private String name;
    private String description;
    private LinkedList<Ingredient> ingredientList = new LinkedList<>();

    private boolean active = true;

    protected Dish(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    protected Dish(String name, String description) {
        this(null, name, description);
    }

    public void addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }
    public void addIngredientList(List<Ingredient> list) {
        ingredientList.addAll(list);
    }
    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract String getType();

}
