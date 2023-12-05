package com.pickyeaters.app.model;

public class DishFirst extends Dish{
    public DishFirst(int id, String name, Ingredient[] ingredientList) {
        super(id, name, ingredientList);
        this.type = TYPE_FIRST;
    }
}
