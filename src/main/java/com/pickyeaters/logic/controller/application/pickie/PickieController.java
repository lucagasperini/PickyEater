package com.pickyeaters.logic.controller.application.pickie;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;

public class PickieController extends VirtualController {
    private FindRestaurantController findRestaurant = new FindRestaurantController(main);
    private EatingPreferencesController eatingPreferences = new EatingPreferencesController(main);
    private ReviewDishController reviewDish = new ReviewDishController(main);
    public PickieController(MainController main) {
        super(main);
    }

    public EatingPreferencesController getEatingPreferences() {
        return eatingPreferences;
    }

    public ReviewDishController getReviewDish() {
        return reviewDish;
    }

    public FindRestaurantController getFindRestaurant() {
        return findRestaurant;
    }
}
