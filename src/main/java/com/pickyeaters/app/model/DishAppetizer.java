package com.pickyeaters.app.model;

public class DishAppetizer extends Dish {
    public DishAppetizer(String name, Ingredient[] ingredientList) {
        super(name, ingredientList);
        this.type = Dish.TYPE_APPETIZER;
    }
}
