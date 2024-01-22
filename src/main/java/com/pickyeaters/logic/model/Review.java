package com.pickyeaters.logic.model;

import jdk.jfr.Unsigned;

public class Review {
    private Pickie reviewer;
    private Restaurant reviewedRestaurant;
    private Dish reviewedDish;
    private Unsigned grade; //TODO: check if it's the right datatype
}
