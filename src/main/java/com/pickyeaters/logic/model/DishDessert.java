package com.pickyeaters.logic.model;

public class DishDessert extends Dish {
    public DishDessert(String name, String description) {
        super(name, description);
    }

    @Override
    public String getType() {
        return TYPE_DESSERT;
    }
}
