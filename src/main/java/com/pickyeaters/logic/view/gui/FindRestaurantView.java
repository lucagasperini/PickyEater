package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.pickie.FindRestaurantController;
import javafx.scene.layout.BorderPane;

public class FindRestaurantView extends VirtualPaneView {
    private FindRestaurantController controller;
    public FindRestaurantView(FindRestaurantController controller, VirtualPaneView parent) {
        super("/form/pickie/findARestaurantView.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup() {

    }
}
