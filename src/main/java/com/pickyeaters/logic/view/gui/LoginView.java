package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.controller.MainController;
import com.pickyeaters.logic.controller.SettingsController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class LoginView extends VirtualWindowView {
    public LoginView(MainController controller) {
        super(controller, "/loginView.fxml");
    }

    @FXML
    private TextField inputLoginEmail;
    @FXML
    private PasswordField inputLoginPassword;
    @FXML
    private Button inputLogin;
    @FXML
    private Text textLoginEmail;
    @FXML
    private Text textLoginPassword;

    protected void setup() {
        textLoginEmail.setText(SettingsController.i18n("GUI_LOGIN_VIEW_EMAIL"));
        textLoginPassword.setText(SettingsController.i18n("GUI_LOGIN_VIEW_PASSWORD"));
        inputLogin.setText(SettingsController.i18n("GUI_LOGIN_VIEW_LOGIN"));
    }
    @FXML protected void clickLogin(ActionEvent event) {

        LoginBean loginBean = new LoginBean(
                inputLoginEmail.getText(),
                inputLoginPassword.getText()
        );

        try {
            controller.getLoginController().auth(loginBean);
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
