package com.pickyeaters.app.model;

public class DishDrink extends Dish {
    public DishDrink(String name, Ingredient[] ingredientList) {
        super(name, ingredientList);
        this.type = TYPE_DRINK;
    }
}
