package com.pickyeaters.app.model;

public class DishDessert extends Dish {
    public DishDessert(String name, Ingredient[] ingredientList) {
        super(name, ingredientList);
        this.type = TYPE_DESSERT;
    }
}
