package com.pickyeaters.app.view.gui;

import com.pickyeaters.app.view.bean.LoginBean;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView extends VirtualView {
    public LoginView(MainController controller) {
        super(controller, "/form/login.fxml");
    }
    @Override
    public void show() {
        setup();
        stage.setScene(new Scene(root, 600, 400));
        stage.showAndWait();
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
        labelLoginUsername.setText(SettingsController.i18n("PICKY_GUI_LOGIN_USERNAME"));
        labelLoginPassword.setText(SettingsController.i18n("PICKY_GUI_LOGIN_PASSWORD"));
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
