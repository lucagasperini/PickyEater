package com.pickyeaters.logic.view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

public abstract class VirtualWidget {
    Parent root;

    public VirtualWidget(String fxml) {
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
