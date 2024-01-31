package com.pickyeaters.logic.view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Map;

public abstract class VirtualWidget extends VirtualViewGUI {
    Parent root;
    private VirtualPaneView parent;

    protected VirtualWidget(String fxml, VirtualPaneView parent) {
        super(fxml);
        this.parent = parent;
    }

    protected void toParent(Map<String, String> arg) {
        if(parent != null) {
            parent.setup(arg);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }
}
