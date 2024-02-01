package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginView extends VirtualPaneView {
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
    protected Text textTitle;
    @FXML
    protected Text textSubtitle;

    private final LoginController controller;

    public LoginView(LoginController controller, VirtualPaneView parent) {
        super("/form/Login.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {
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
            controller.auth(loginBean);
            Map<String, String> result = new HashMap<>();
            result.put("login", "true");
            showParent(result);
        } catch (LoginControllerException ex) {
            showError(ex);
        }
    }

    @FXML
    private void clickButtonBack(ActionEvent event) {
        VirtualPaneView.getActiveView().showParent();
    }
}
