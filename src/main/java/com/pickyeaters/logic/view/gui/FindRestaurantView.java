package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.pickie.FindRestaurantController;

public class FindRestaurantView extends VirtualPaneView {
    private FindRestaurantController controller;
    public FindRestaurantView(FindRestaurantController controller, VirtualPaneView parent) {
        super("/form/pickie/FindRestaurant.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup() {

    }
}
