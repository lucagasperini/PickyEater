package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class LoginView extends VirtualWindowView {
    public LoginView(MainController controller) {
        super(controller, "/form/login.fxml");
    }

    @FXML
    private TextField inputLoginUsername;
    @FXML
    private PasswordField inputLoginPassword;
    @FXML
    private Button inputLogin;
    @FXML
    private Label labelLoginUsername;
    @FXML
    private Label labelLoginPassword;

    protected void setup() {
        labelLoginUsername.setText(SettingsController.i18n("GUI_LOGIN_VIEW_USERNAME"));
        labelLoginPassword.setText(SettingsController.i18n("GUI_LOGIN_VIEW_PASSWORD"));
        inputLogin.setText(SettingsController.i18n("PICKY_GUI_LOGIN_LOGIN"));
    }
    @FXML protected void clickLogin(ActionEvent event) {

        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(inputLoginUsername.getText());
        loginBean.setPassword(inputLoginPassword.getText());

        try {
            controller.getLoginController().login(loginBean);
            stage.close();
        } catch (LoginControllerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(SettingsController.i18n("MSG_INVALID_LOGIN_TITLE"));
            alert.setHeaderText(SettingsController.i18n("MSG_INVALID_LOGIN_HEADER"));
            alert.setContentText(SettingsController.i18n("MSG_INVALID_LOGIN_CONTENT"));
            alert.showAndWait();
        }
    }

}
