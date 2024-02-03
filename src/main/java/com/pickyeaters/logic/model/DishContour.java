package com.pickyeaters.logic.model;

public class DishContour extends Dish {
    public DishContour(){}
    public DishContour(String name, String description) {
        super(name, description);
    }

    @Override
    public String getType() {
        return TYPE_CONTOUR;
    }
}
