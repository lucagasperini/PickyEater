package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.application.administrator.AdministratorController;
import com.pickyeaters.logic.controller.application.pickie.PickieController;
import com.pickyeaters.logic.controller.application.restaurateur.RestaurateurController;

public class MainController {
    private InitController initController = null;
    private LoginController loginController = null;
    private RestaurateurController restaurateurController = null;
    private PickieController pickieController = null;
    private AdministratorController administratorController = null;
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

    public PickieController getPickieController() {
        return pickieController;
    }

    public AdministratorController getAdministratorController() {
        return administratorController;
    }

    public void start() {
        isRunning = true;
        initController = new InitController(this);
        loginController = new LoginController(this);
    }

    public void initRestaurateur() {
        restaurateurController = new RestaurateurController(this);
    }
    public void initPickie() {
        pickieController = new PickieController(this);
    }
    public void initAdministrator() {
        administratorController = new AdministratorController(this);
    }

    public boolean isRunning() {
        return isRunning;
    }
    public void quit() {
        isRunning = false;
    }
}
