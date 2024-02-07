package com.pickyeaters.logic.view.cli.pickie;

import com.pickyeaters.logic.controller.application.pickie.FindRestaurantController;
import com.pickyeaters.logic.view.cli.VirtualRequestView;

import java.util.Map;

public class FindRestaurantView extends VirtualRequestView {
    private final FindRestaurantController controller = new FindRestaurantController();
    public FindRestaurantView() {
        super("FindRestaurant", "");
    }

    @Override
    public void show(Map<String, String> arg) {
        requestLoop();
    }

    @Override
    protected boolean request(String request) {
        return false;
    }

    @Override
    protected String requestHelp() {
        return null;
    }
}
