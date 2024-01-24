package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class VirtualView {
    protected MainController controller = null;
    protected URL fxml = null;
    protected Parent root = null;
    protected Stage stage = null;
    public VirtualView(MainController controller, String fxml) {
        this(controller, fxml, new Stage());
    }
    public VirtualView(MainController controller, String fxml, Stage stage) {
        this.controller = controller;
        this.fxml = getClass().getResource(fxml);
        this.stage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.fxml);
            loader.setController(this);
            this.root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            //TODO:
            System.exit(-1);
        }
    }

    protected abstract void setup();

    public abstract void show();
}
