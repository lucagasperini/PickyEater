package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.view.gui.FindRestaurantView;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class PickieHomeView extends VirtualPaneView {
    public PickieHomeView(MainController controller, BorderPane mainLayout) {
        super(controller, "/form/pickie/homeView.fxml", mainLayout);
    }

    @FXML
    protected void clickFindRestaurant(ActionEvent event) {
        FindRestaurantView findRestaurantView = new FindRestaurantView(controller, mainLayout);
        findRestaurantView.show();
    }
}
