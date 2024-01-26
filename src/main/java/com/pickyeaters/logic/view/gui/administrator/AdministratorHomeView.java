package com.pickyeaters.logic.view.gui.administrator;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.scene.layout.BorderPane;

public class AdministratorHomeView extends VirtualPaneView {
    public AdministratorHomeView(MainController controller, BorderPane mainLayout) {
        super(controller, "/form/administrator/homeView.fxml", mainLayout);
    }
}
