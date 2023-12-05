package com.pickyeater.app.model;

public class DishAppetizer extends Dish {
    public DishAppetizer(int id, String name, Ingredient[] ingredientList) {
        super(id, name, ingredientList);
        this.type = Dish.TYPE_APPETIZER;
    }
}
