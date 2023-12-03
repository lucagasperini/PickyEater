package com.pickyeater.lib.model;

public class DishDessert extends Dish {
    public DishDessert(int id, String name, Ingredient[] ingredientList) {
        super(id, name, ingredientList);
        this.type = TYPE_DESSERT;
    }
}
