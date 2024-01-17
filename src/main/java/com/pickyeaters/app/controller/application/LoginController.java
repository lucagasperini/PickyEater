package com.pickyeaters.app.controller.application;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.controller.exception.DatabaseControllerException;
import com.pickyeaters.app.controller.exception.LoginControllerException;

public class LoginController {

    private Session session = null;

    public LoginController() {

    }

    public void login(String username, String password) throws LoginControllerException, DatabaseControllerException {
        session = DatabaseController.getInstance().login(username, password);

        if(!session.isValid()) {
            throw new LoginControllerException("Login failed");
        }
    }
}
