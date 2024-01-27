package com.pickyeaters.logic.controller.application;

public class MainController {
    private InitController initController = null;
    private LoginController loginController = null;
    private RestaurateurController restaurateurController = null;
    private boolean isRunning = false;

    public InitController getInitController() {
        return initController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public RestaurateurController getRestaurateurController() {
        return restaurateurController;
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
