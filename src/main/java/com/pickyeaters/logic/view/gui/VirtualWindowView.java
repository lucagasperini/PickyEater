package com.pickyeaters.logic.view.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public abstract class VirtualWindowView extends VirtualViewGUI {
    protected Stage stage = new Stage();
    protected VirtualWindowView(String fxml) {
        super(fxml);
    }

    protected abstract void setup(Map<String, String> arg);

    public void show() {
        setup(null);
        stage.setScene(new Scene(getRoot(), 1280, 720));
        stage.showAndWait();
    }
}
