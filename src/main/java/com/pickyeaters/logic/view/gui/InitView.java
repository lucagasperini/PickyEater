package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.InitController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.SettingsBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.Map;

public class InitView extends VirtualPaneView {
    @FXML
    private TextField inputDatabaseHost;
    @FXML
    private TextField inputDatabasePort;
    @FXML
    private TextField inputDatabaseName;
    @FXML
    private TextField inputDatabaseUser;
    @FXML
    private PasswordField inputDatabasePassword;
    @FXML
    private ComboBox<String> comboBoxLocale;
    @FXML
    private Text textDatabaseHost;
    @FXML
    private Text textDatabasePort;
    @FXML
    private Text textDatabaseName;
    @FXML
    private Text textDatabaseUser;
    @FXML
    private Text textDatabasePassword;
    @FXML
    private Text textLocale;
    @FXML
    private Button buttonSave;
    private final InitController controller;
    public InitView(InitController controller, VirtualPaneView parent) {
        super("/form/Init.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {
        textDatabaseHost.setText("Database Host");
        textDatabasePort.setText("Database Port");
        textDatabaseName.setText("Database Name");
        textDatabaseUser.setText("Database User");
        textDatabasePassword.setText("Database Password");
        textLocale.setText("Locale");
        buttonSave.setText("Save");
        comboBoxLocale.getItems().addAll("it", "en");
        comboBoxLocale.getSelectionModel().select(0);
    }

    @FXML protected void clickButtonSave(ActionEvent event) {
        SettingsBean settings = new SettingsBean();

        settings.setDatabaseHost(inputDatabaseHost.getText());
        settings.setDatabasePort(inputDatabasePort.getText());
        settings.setDatabaseName(inputDatabaseName.getText());
        settings.setDatabaseUser(inputDatabaseUser.getText());
        settings.setDatabasePassword(inputDatabasePassword.getText());

        try {
            controller.loadFromInput(settings);
            showParent();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid settings");
            alert.setHeaderText("Invalid settings");
            alert.setContentText("Cannot open database or settings");
            alert.showAndWait();
        }
    }
}
