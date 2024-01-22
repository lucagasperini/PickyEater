package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.model.Session;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;

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
