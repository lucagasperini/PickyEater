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
import java.util.Map;

public abstract class VirtualWindowView extends VirtualViewGUI {
    protected Stage stage = new Stage();
    private final int WINDOW_HEIGTH = 720;
    private final int WINDOW_WIDTH = 1280;
    public VirtualWindowView(String fxml) {
        super(fxml);
    }

    protected abstract void setup(Map<String, String> arg);

    public void show() {
        setup(null);
        stage.setScene(new Scene(getRoot(), WINDOW_WIDTH, WINDOW_HEIGTH));
        stage.showAndWait();
    }
}
