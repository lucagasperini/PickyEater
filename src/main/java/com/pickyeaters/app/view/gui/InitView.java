package com.pickyeaters.app.view.gui;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.app.view.bean.SettingsBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InitView extends VirtualView {
    public InitView(MainController controller) {
        super(controller, "/form/database.fxml");
    }

    @Override
    public void show() {
        stage.setScene(new Scene(root, 600, 400));
        stage.showAndWait();
    }

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

    @FXML protected void clickDatabaseConnect(ActionEvent event) {
        SettingsBean settings = new SettingsBean();

        // NOTE: Forcing this driver
        settings.setDatabaseDriver("postgresql");
        settings.setDatabaseHost(inputDatabaseHost.getText());
        settings.setDatabasePort(inputDatabasePort.getText());
        settings.setDatabaseName(inputDatabaseName.getText());
        settings.setDatabaseUser(inputDatabaseUsername.getText());
        settings.setDatabasePassword(inputDatabasePassword.getText());

        try {
            controller.getInitController().loadFromInput(settings);
            stage.close();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("MSG_INVALID_INIT_TITLE");
            alert.setHeaderText("MSG_INVALID_INIT_HEADER");
            alert.setContentText("MSG_INVALID_INIT_CONTENT");
            alert.showAndWait();
        }
    }

}
