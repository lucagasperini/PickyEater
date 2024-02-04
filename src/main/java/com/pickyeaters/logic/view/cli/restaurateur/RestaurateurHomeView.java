package com.pickyeaters.logic.view.cli.restaurateur;

import com.pickyeaters.logic.view.cli.VirtualRequestView;

import java.util.Map;

public class RestaurateurHomeView extends VirtualRequestView {
    public RestaurateurHomeView() {
        super("Home");
    }

    @Override
    public void show(Map<String, String> arg) {
        requestLoop();
    }

    public boolean request(String request) {
        switch (request) {
            case "restaurant", "r":
                RestaurantDetailsView restaurantDetailsView = new RestaurantDetailsView();
                restaurantDetailsView.show();
                return true;
            case "menu", "m":
                MenuDetailsView menuDetailsView = new MenuDetailsView();
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
