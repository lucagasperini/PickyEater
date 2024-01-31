package com.pickyeaters.logic.model;

public class DishSecond extends Dish {
    public DishSecond(String name, String description) {
        super(name, description);
    }

    @Override
    public String getType() {
        return TYPE_SECOND;
    }
}
