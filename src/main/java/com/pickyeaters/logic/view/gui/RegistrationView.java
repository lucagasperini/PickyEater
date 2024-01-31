package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.LoginController;

import java.util.Map;

public class RegistrationView extends VirtualPaneView {
    LoginController controller;
    public RegistrationView(LoginController controller, VirtualPaneView parent) {
        super("/form/Registration.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {

    }
}
