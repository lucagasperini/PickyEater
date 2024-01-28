package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.view.VirtualView;
import com.pickyeaters.logic.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public abstract class VirtualPaneView extends VirtualView {
    protected URL fxml = null;
    protected BorderPane mainLayout = null;
    protected Parent root = null;
    public VirtualPaneView(MainController controller, String fxml, BorderPane mainLayout) {
        super(controller);
        FXMLLoader loader = new FXMLLoader();
        this.fxml = getClass().getResource(fxml);
        loader.setLocation(this.fxml);
        loader.setController(this);
        this.mainLayout = mainLayout;
        try {
            this.root = loader.load();
        } catch (IOException ex) {
            System.err.println("[FXML] FATAL ERROR: " + ex.getMessage());
            //TODO:
            System.exit(-1);
        }
    }

    public void show() {
        mainLayout.setCenter(this.root);
    }
}
