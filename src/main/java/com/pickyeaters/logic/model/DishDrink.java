package com.pickyeaters.logic.model;

public class DishDrink extends Dish {
    public DishDrink() {}
    public DishDrink(String name, String description) {
        super(name, description);
    }

    @Override
    public String getType() {
        return TYPE_DRINK;
    }
}
