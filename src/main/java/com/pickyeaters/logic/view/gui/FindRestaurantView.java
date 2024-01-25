package com.pickyeaters.app.view.gui;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FindRestaurantView extends VirtualPaneView {
    public FindRestaurantView(MainController controller, BorderPane mainLayout) {
        super(controller, "/pickie_findARestaurantView.fxml", mainLayout);
    }
}
