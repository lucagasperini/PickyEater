package com.pickyeaters.logic.model;

public class DishAppetizer extends Dish {
    public DishAppetizer(String name, String description) {
        super(name, description);
    }

    @Override
    public String getType() {
        return TYPE_APPETIZER;
    }
}
