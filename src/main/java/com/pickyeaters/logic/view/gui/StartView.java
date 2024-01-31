package com.pickyeaters.logic.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

public class StartView extends VirtualPaneView {
    @FXML
    private Button buttonRegistration;
    @FXML
    private Button buttonLogin;
    @FXML
    private Text textWelcome;
    @FXML
    private BorderPane mainLayout;
    private Stage stage;

    public StartView() {
        super("/form/Start.fxml", null);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        if(arg != null) {
            setupLogin(arg.get("login"));
        }
    }

    private void setupLogin(String login) {
        if(login != null) {
            getMainView().showNavbar();
            getMainView().showHomeView();
        }
    }


    @FXML
    private void clickButtonLogin(ActionEvent actionEvent) {
        LoginView loginView = new LoginView(getMainController().getLogin(), this);
        loginView.show();
    }
    @FXML
    private void clickButtonRegistration(ActionEvent actionEvent) {
        RegistrationView registrationView = new RegistrationView(getMainController().getLogin(), this);
        registrationView.show();
    }
}
