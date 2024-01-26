package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.view.VirtualView;
import com.pickyeaters.logic.controller.application.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class VirtualWindowView extends VirtualView {
    protected URL fxml = null;
    protected Parent root = null;
    protected Stage stage = new Stage();
    private final int WINDOW_HEIGTH = 720;
    private final int WINDOW_WIDTH = 1280;
    public VirtualWindowView(MainController controller, String fxml) {
        super(controller);
        FXMLLoader loader = new FXMLLoader();
        this.fxml = getClass().getResource(fxml);
        loader.setLocation(this.fxml);
        loader.setController(this);
        try {
            this.root = loader.load();
        } catch (IOException ex) {
            System.err.println("[FXML] FATAL ERROR: " + ex.getMessage());
            //TODO:
            System.exit(-1);
        }

        setup();
    }

    protected abstract void setup();

    public void show() {
        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGTH));
        stage.showAndWait();
    }
}
