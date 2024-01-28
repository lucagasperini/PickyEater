package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;

public class RestaurateurController extends VirtualController {
    public RestaurateurController(MainController main) {
        super(main);
    }
    private RestaurantDetailsController restaurantDetails = new RestaurantDetailsController(main);
    private MenuDetailsController menuDetails = new MenuDetailsController(main);

    public MenuDetailsController getMenuDetails() {
        return menuDetails;
    }

    public RestaurantDetailsController getRestaurantDetails() {
        return restaurantDetails;
    }
}
