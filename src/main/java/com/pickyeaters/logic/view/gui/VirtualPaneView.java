package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.view.VirtualView;
import com.pickyeaters.logic.controller.application.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class VirtualPaneView extends VirtualView {
    private static BorderPane mainLayout;
    protected URL fxml = null;
    private VirtualPaneView parent;
    protected Parent root = null;
    public VirtualPaneView(MainController controller, String fxml, VirtualPaneView parent) {
        super(controller);
        FXMLLoader loader = new FXMLLoader();
        this.fxml = getClass().getResource(fxml);
        loader.setLocation(this.fxml);
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

    protected static void setMainLayout(BorderPane mainLayout) {
        VirtualPaneView.mainLayout = mainLayout;
    }

    protected abstract void setup();

    public void show() {
        setup();
        mainLayout.setCenter(this.root);
    }
    public void showParent() {
        if(parent != null) {
            parent.setup();
            mainLayout.setCenter(parent.root);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }
}
