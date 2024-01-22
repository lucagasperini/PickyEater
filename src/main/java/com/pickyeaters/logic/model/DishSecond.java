package com.pickyeaters.logic.model;

public class DishSecond extends Dish {
    public DishSecond(String name, Ingredient[] ingredientList) {
        super(name, ingredientList);
        this.type = TYPE_SECOND;
    }
}
