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
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;

    public LoginView(MainController controller) {
        super(controller, "/form/Login.fxml");
        textLoginEmail.setText(SettingsController.i18n("LOGIN_EMAIL"));
        textLoginPassword.setText(SettingsController.i18n("LOGIN_PASSWORD"));
        inputLogin.setText(SettingsController.i18n("LOGIN_BUTTON"));
        textTitle.setText(SettingsController.i18n("LOGIN_TITLE"));
        textSubtitle.setText(SettingsController.i18n("LOGIN_SUBTITLE"));
    }

    @FXML protected void clickLogin(ActionEvent event) {

        LoginBean loginBean = new LoginBean(
                inputLoginEmail.getText(),
                inputLoginPassword.getText()
        );

        try {
            controller.getLogin().auth(loginBean);
            stage.close();
        } catch (LoginControllerException ex) {
            //TODO: Create different messages if bad auth or internal error!
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(SettingsController.i18n("LOGIN_MSG_INVALID_TITLE"));
            alert.setHeaderText(SettingsController.i18n("LOGIN_MSG_INVALID_HEADER"));
            alert.setContentText(SettingsController.i18n("LOGIN_MSG_INVALID_CONTENT"));
            alert.showAndWait();
        }
    }

}
