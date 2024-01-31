package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.Main;
import com.pickyeaters.logic.view.VirtualView;
import com.pickyeaters.logic.controller.application.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public abstract class VirtualPaneView {
    private static BorderPane mainLayout;
    protected URL fxml = null;
    private VirtualPaneView parent;
    protected Parent root = null;
    private static MainController mainController;
    public VirtualPaneView(String fxml, VirtualPaneView parent) {
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
    protected static void init(MainController mainController, BorderPane mainLayout) {
        VirtualPaneView.mainController = mainController;
        VirtualPaneView.mainLayout = mainLayout;
    }

    public static MainController getMainController() {
        return mainController;
    }

    protected abstract void setup(Map<String, String> arg);

    public void show(Map<String, String> arg) {
        setup(arg);
        mainLayout.setCenter(this.root);
    }
    public void show() {
        setup(null);
        mainLayout.setCenter(this.root);
    }

    public void showParent() {
        if(parent != null) {
            parent.setup(null);
            mainLayout.setCenter(parent.root);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }
    public void showParent(Map<String, String> arg) {
        if(parent != null) {
            parent.setup(arg);
            mainLayout.setCenter(parent.root);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }
}
