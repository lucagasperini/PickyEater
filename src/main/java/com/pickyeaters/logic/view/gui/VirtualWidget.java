package com.pickyeaters.logic.view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Map;

public abstract class VirtualWidget {
    Parent root;
    private VirtualPaneView parent;

    public VirtualWidget(String fxml, VirtualPaneView parent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        loader.setController(this);
        this.parent = parent;
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
    protected void toParent(Map<String, String> arg) {
        if(parent != null) {
            parent.setup(arg);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }
}
