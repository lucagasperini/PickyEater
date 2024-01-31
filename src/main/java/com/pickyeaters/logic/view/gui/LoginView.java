package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class LoginView extends VirtualWindowView {
    @FXML
    private TextField inputEmail;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Button buttonLogin;
    @FXML
    private Text textEmail;
    @FXML
    private Text textPassword;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private Button buttonBack;

    public LoginView(MainController controller) {
        super(controller, "/form/Login.fxml");
        buttonBack.setText(SettingsController.i18n("BACK"));
        textEmail.setText(SettingsController.i18n("LOGIN_EMAIL"));
        textPassword.setText(SettingsController.i18n("LOGIN_PASSWORD"));
        buttonLogin.setText(SettingsController.i18n("LOGIN_LOGIN"));
        textTitle.setText(SettingsController.i18n("LOGIN_TITLE"));
        textSubtitle.setText(SettingsController.i18n("LOGIN_SUBTITLE"));
    }

    @FXML
    private void clickButtonLogin(ActionEvent event) {

        LoginBean loginBean = new LoginBean(
                inputEmail.getText(),
                inputPassword.getText()
        );

        try {
            controller.getLogin().auth(loginBean);
            stage.close();
        } catch (LoginControllerException ex) {
            //TODO: Create different messages if bad auth or internal error!
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(SettingsController.i18n("ERROR_LOGIN_TITLE"));
            alert.setHeaderText(SettingsController.i18n("ERROR_LOGIN_HEADER"));
            alert.setContentText(SettingsController.i18n("ERROR_LOGIN_CONTENT"));
            alert.showAndWait();
        }
    }

    @FXML
    private void clickButtonBack(ActionEvent event) {
    }
}
