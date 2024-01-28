package com.pickyeaters.logic.controller;

public class MainController {
    private InitController initController = null;
    private LoginController loginController = null;
    private boolean isRunning = false;

    public InitController getInitController() {
        return initController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void start() {
        isRunning = true;
        initController = new InitController();
        loginController = new LoginController();
    }


    public boolean isRunning() {
        return isRunning;
    }
    public void quit() {
        isRunning = false;
    }
}
