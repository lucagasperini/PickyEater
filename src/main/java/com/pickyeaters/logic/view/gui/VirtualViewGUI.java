package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Map;

public abstract class VirtualViewGUI {
    private Parent root = null;
    public VirtualViewGUI(String fxml) {
        loadFXML(fxml);
    }

    private void loadFXML(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        loader.setController(this);
        try {
            this.root = loader.load();
        } catch (IOException ex) {
            System.err.println("[FXML] FATAL ERROR: " + ex.getMessage());
            //TODO:
            System.exit(-1);
        }
    }

    public Parent getRoot() {
        return root;
    }
}
