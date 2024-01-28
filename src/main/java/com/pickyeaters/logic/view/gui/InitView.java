package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.MainController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.SettingsBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InitView extends VirtualWindowView {
    @FXML
    private TextField inputDatabaseHost;
    @FXML
    private TextField inputDatabasePort;
    @FXML
    private TextField inputDatabaseName;
    @FXML
    private TextField inputDatabaseUsername;
    @FXML
    private PasswordField inputDatabasePassword;
    public InitView(MainController controller) {
        super(controller, "/form/Init.fxml");
    }

    @FXML protected void clickDatabaseConnect(ActionEvent event) {
        SettingsBean settings = new SettingsBean();

        settings.setDatabaseHost(inputDatabaseHost.getText());
        settings.setDatabasePort(inputDatabasePort.getText());
        settings.setDatabaseName(inputDatabaseName.getText());
        settings.setDatabaseUser(inputDatabaseUsername.getText());
        settings.setDatabasePassword(inputDatabasePassword.getText());

        try {
            controller.getInit().loadFromInput(settings);
            stage.close();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid settings");
            alert.setHeaderText("Invalid settings");
            alert.setContentText("Cannot open database or settings");
            alert.showAndWait();
        }
    }

}
