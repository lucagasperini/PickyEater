package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.scene.layout.BorderPane;

public class RestaurateurHomeView extends VirtualPaneView {
    public RestaurateurHomeView(MainController controller, BorderPane mainLayout) {
        super(controller, "/restaurateur_homeView.fxml", mainLayout);
    }

}
