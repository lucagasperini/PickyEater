package com.pickyeaters.app.model;

public class DishContour extends Dish {
    public DishContour(String name, Ingredient[] ingredientList) {
        super(name, ingredientList);
        this.type = TYPE_CONTOUR;
    }
}
