package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.Map;

public abstract class VirtualViewGUI {
    private Parent root = null;
    public VirtualViewGUI(String fxml) {
        loadFXML(fxml);
    }

    private void loadFXML(String fxml) {
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

    public void showError(String key) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(key.isEmpty()) {
            key = "DEFAULT";
        }

        alert.setTitle(SettingsController.i18n(key + "_ALERT_ERROR_TITLE"));
        alert.setHeaderText(SettingsController.i18n(key + "_ALERT_ERROR_HEADER"));
        alert.setContentText(SettingsController.i18n(key + "_ALERT_ERROR_CONTENT"));

        alert.showAndWait();
    }

    public void showError(ControllerException ex) {
        showError(ex.getKey());
    }

    public Parent getRoot() {
        return root;
    }
}
