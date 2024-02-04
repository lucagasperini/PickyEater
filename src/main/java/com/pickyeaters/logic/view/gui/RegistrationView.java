package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.LoginController;

import java.util.Map;

public class RegistrationView extends VirtualPaneView {
    private final LoginController controller = new LoginController();
    public RegistrationView(VirtualPaneView parent) {
        super("/form/Registration.fxml", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        throw new UnsupportedOperationException();
    }
}
