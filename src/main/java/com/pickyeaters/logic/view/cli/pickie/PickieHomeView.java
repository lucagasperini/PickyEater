package com.pickyeaters.logic.view.cli.pickie;

import com.pickyeaters.logic.controller.application.pickie.PickieController;
import com.pickyeaters.logic.view.cli.VirtualRequestView;
import com.pickyeaters.logic.view.cli.restaurateur.MenuDetailsView;
import com.pickyeaters.logic.view.cli.restaurateur.RestaurantDetailsView;

import java.util.Map;

public class PickieHomeView extends VirtualRequestView {
    private final PickieController controller;
    public PickieHomeView(PickieController controller) {
        super("Home");
        this.controller = controller;
    }

    @Override
    public void show(Map<String, String> arg) {

    }

    @Override
    protected boolean request(String request) {
        switch (request) {
            case "find", "f":
                FindRestaurantView findRestaurantView = new FindRestaurantView(controller.getFindRestaurant());
                findRestaurantView.show();
                return true;
            case "pref", "p":
                return true;
            case "review", "r":
                return true;
            default:
                return false;
        }
    }

    @Override
    protected String requestHelp() {
        return """
                [find, f]
                [pref, p]
                [review, r]""";
    }
}
