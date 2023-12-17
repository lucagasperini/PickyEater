package com.pickyeaters.app.view.controller;

import com.pickyeaters.app.bean.LoginBean;
import com.pickyeaters.app.bean.SettingsBean;
import com.pickyeaters.app.controller.DatabaseController;
import com.pickyeaters.app.controller.SessionController;
import com.pickyeaters.app.controller.SettingsController;
import com.pickyeaters.app.utils.DatabaseControllerException;
import com.pickyeaters.app.utils.SessionControllerException;
import com.pickyeaters.app.utils.SettingsControllerException;

public class InitViewController {

    private SessionController sessionController = new SessionController();
    public void loadFromFile() throws SettingsControllerException, DatabaseControllerException {
        // Try to load config from file system
        SettingsController.getInstance().init();
        // try to init database connection
        DatabaseController.getInstance().init();
        // try to save current config on file
        SettingsController.getInstance().persist();
    }

    public void loadFromInput(SettingsBean settingsBean) throws SettingsControllerException, DatabaseControllerException {
        // Try to load config from settingsBean
        SettingsController.getInstance().init(settingsBean);
        // try to init database connection
        DatabaseController.getInstance().init();
        // try to save current config on file
        SettingsController.getInstance().persist();
    }

    public void login(LoginBean loginBean) throws SessionControllerException, DatabaseControllerException {
        sessionController.login(loginBean.getUsername(), loginBean.getPassword());
    }
}
