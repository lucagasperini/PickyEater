package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainPickieView extends VirtualPaneView {
    public MainPickieView(MainController controller, BorderPane mainLayout) {
        super(controller, "/pickie_homeView.fxml", mainLayout);
    }

    @FXML
    protected void clickFindRestaurant(ActionEvent event) {
        System.out.println("lfds");
        FindRestaurantView findRestaurantView = new FindRestaurantView(controller, mainLayout);
        findRestaurantView.show();
    }
}
