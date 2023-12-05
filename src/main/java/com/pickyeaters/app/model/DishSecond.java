package com.pickyeaters.app.model;

public class DishSecond extends Dish {
    public DishSecond(int id, String name, Ingredient[] ingredientList) {
        super(id, name, ingredientList);
        this.type = TYPE_SECOND;
    }
}
