package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.utils.DatabaseControllerException;

public class SessionController {
    private static SessionController instance = new SessionController();

    public static SessionController getInstance() {
        return instance;
    }

    private SessionController() {

    }

    public Session login(String username, String password) {
        Session session = null;
        try {
            session = DatabaseController.getInstance().login(username, password);
        } catch (DatabaseControllerException ex) {
            session = new Session();
            // TODO: Do something, anyway think about a rework
        }
        return session;
    }
}
