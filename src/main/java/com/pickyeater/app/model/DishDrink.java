package com.pickyeater.app.model;

public class DishDrink extends Dish {
    public DishDrink(int id, String name, Ingredient[] ingredientList) {
        super(id, name, ingredientList);
        this.type = TYPE_DRINK;
    }
}
