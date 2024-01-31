package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.FindRestaurantController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class FindRestaurantView extends VirtualPaneView {
    private FindRestaurantController controller;
    public FindRestaurantView(FindRestaurantController controller, VirtualPaneView parent) {
        super("/form/pickie/FindRestaurant.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {

    }
}
