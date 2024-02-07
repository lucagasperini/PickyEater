package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.VirtualException;
import com.pickyeaters.logic.view.VirtualView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;

public abstract class VirtualViewGUI extends VirtualView {
    private Parent root = null;

    protected VirtualViewGUI(String fxml, String resource) {
        super(resource);
        loadFXML(fxml);
    }

    private void loadFXML(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        loader.setController(this);
        try {
            this.root = loader.load();
        } catch (IOException ex) {
            showError("FXML ERROR", "FXML ERROR", ex.getMessage());
        }
    }

    protected void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    protected void showError(String key) {
        if(key.isEmpty()) {
            showError(
                    "DEFAULT_ALERT_ERROR_TITLE",
                    "DEFAULT_ALERT_ERROR_HEADER",
                    "DEFAULT_ALERT_ERROR_CONTENT"
            );
        } else {
            showError(
                    i18nGlobal(key + "_ALERT_ERROR_TITLE"),
                    i18nGlobal(key + "_ALERT_ERROR_HEADER"),
                    i18nGlobal(key + "_ALERT_ERROR_CONTENT")
            );
        }
    }

    protected void showError(VirtualException ex) {
        showError(ex.getKey());
    }

    public Parent getRoot() {
        return root;
    }

}
