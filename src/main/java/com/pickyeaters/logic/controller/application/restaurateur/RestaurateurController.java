package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.application.restaurateur.ProvideMenuDetailsController;
import com.pickyeaters.logic.controller.application.restaurateur.ProvideRestaurantDetailsController;

public class RestaurateurController extends VirtualController {
    public RestaurateurController(MainController main) {
        super(main);
    }
    private ProvideRestaurantDetailsController provideRestaurantDetails = new ProvideRestaurantDetailsController(main);
    private ProvideMenuDetailsController provideMenuDetails = new ProvideMenuDetailsController(main);

    public ProvideMenuDetailsController getProvideMenuDetails() {
        return provideMenuDetails;
    }

    public ProvideRestaurantDetailsController getProvideRestaurantDetails() {
        return provideRestaurantDetails;
    }
}
