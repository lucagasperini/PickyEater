package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.utils.DatabaseControllerException;

public class SessionController {
    public static Session login(String username, String password) {
        Session session = null;
        try {
            session = DatabaseController.login(username, password);
        } catch (DatabaseControllerException ex) {
            session = new Session();
            // TODO: Do something, anyway think about a rework
        }
        return session;
    }
}
