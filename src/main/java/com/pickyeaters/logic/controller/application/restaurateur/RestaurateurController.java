package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;

public class RestaurateurController extends VirtualController {
    public RestaurateurController(MainController main) {
        super(main);
    }
    private RestaurantDetailsController provideRestaurantDetails = new RestaurantDetailsController(main);
    private MenuDetailsController provideMenuDetails = new MenuDetailsController(main);

    public MenuDetailsController getProvideMenuDetails() {
        return provideMenuDetails;
    }

    public RestaurantDetailsController getProvideRestaurantDetails() {
        return provideRestaurantDetails;
    }
}
