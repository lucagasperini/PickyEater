package com.pickyeaters.app.model;

public class DishContour extends Dish {
    public DishContour(int id, String name, Ingredient[] ingredientList) {
        super(id, name, ingredientList);
        this.type = TYPE_CONTOUR;
    }
}
