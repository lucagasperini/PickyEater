package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.application.administrator.AdministratorController;
import com.pickyeaters.logic.controller.application.pickie.PickieController;
import com.pickyeaters.logic.controller.application.restaurateur.RestaurateurController;

public class MainController {
    private InitController init = null;
    private LoginController login = null;
    private RestaurateurController restaurateur = null;
    private PickieController pickie = null;
    private AdministratorController administrator = null;
    private boolean isRunning = false;

    public InitController getInit() {
        return init;
    }

    public LoginController getLogin() {
        return login;
    }

    public RestaurateurController getRestaurateur() {
        return restaurateur;
    }

    public PickieController getPickie() {
        return pickie;
    }

    public AdministratorController getAdministrator() {
        return administrator;
    }

    public void start() {
        isRunning = true;
        init = new InitController(this);
        login = new LoginController(this);
    }

    public void initRestaurateur() {
        restaurateur = new RestaurateurController(this);
    }
    public void initPickie() {
        pickie = new PickieController(this);
    }
    public void initAdministrator() {
        administrator = new AdministratorController(this);
    }

    public boolean isRunning() {
        return isRunning;
    }
    public void quit() {
        isRunning = false;
    }
}
