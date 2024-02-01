package com.pickyeaters.logic.view.cli.restaurateur;

import com.pickyeaters.logic.controller.application.restaurateur.RestaurateurController;
import com.pickyeaters.logic.view.cli.VirtualRequestView;

import java.util.Map;

public class RestaurateurHomeView extends VirtualRequestView {
    private RestaurateurController controller;
    public RestaurateurHomeView(RestaurateurController controller) {
        super("Home");
        this.controller = controller;
    }

    @Override
    public void show(Map<String, String> arg) {
        requestLoop();
    }

    public boolean request(String request) {
        switch (request) {
            case "restaurant", "r":
                RestaurantDetailsView restaurantDetailsView = new RestaurantDetailsView(controller.getRestaurantDetails());
                restaurantDetailsView.show();
                return true;
            case "menu", "m":
                MenuDetailsView menuDetailsView = new MenuDetailsView(controller.getMenuDetails());
                menuDetailsView.show();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected String requestHelp() {
        return """
            [restaurant, r]
            [menu, m]""";
    }


}
