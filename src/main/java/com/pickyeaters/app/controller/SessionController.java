package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.controller.exception.DatabaseControllerException;
import com.pickyeaters.app.controller.exception.SessionControllerException;

public class SessionController {

    private Session session = null;

    public SessionController() {

    }

    public void login(String username, String password) throws SessionControllerException, DatabaseControllerException {
        session = DatabaseController.getInstance().login(username, password);

        if(!session.isValid()) {
            throw new SessionControllerException("Login failed");
        }
    }
}
