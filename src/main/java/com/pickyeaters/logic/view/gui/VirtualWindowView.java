package com.pickyeaters.logic.view.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public abstract class VirtualWindowView extends VirtualViewGUI {
    protected Stage stage = new Stage();
    private final int WINDOW_HEIGTH = 720;
    private final int WINDOW_WIDTH = 1280;
    protected VirtualWindowView(String fxml) {
        super(fxml);
    }

    protected abstract void setup(Map<String, String> arg);

    public void show() {
        setup(null);
        stage.setScene(new Scene(getRoot(), WINDOW_WIDTH, WINDOW_HEIGTH));
        stage.showAndWait();
    }
}
