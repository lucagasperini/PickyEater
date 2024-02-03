package com.pickyeaters.logic.model;

public class DishFirst extends Dish{
    public DishFirst() {}
    public DishFirst(String name, String description) {
        super(name, description);
    }

    @Override
    public String getType() {
        return TYPE_FIRST;
    }
}
