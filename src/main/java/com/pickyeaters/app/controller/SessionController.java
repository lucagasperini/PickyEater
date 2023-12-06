package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;

public class SessionController {
    public static Session login(String username, String password) {
        return SqlController.login(username, password);
    }
}
