package com.pickyeaters.app.controller.graphics;

import com.pickyeaters.app.model.bean.LoginBean;
import com.pickyeaters.app.model.bean.SettingsBean;
import com.pickyeaters.app.controller.application.DatabaseController;
import com.pickyeaters.app.controller.application.LoginController;
import com.pickyeaters.app.controller.application.SettingsController;
import com.pickyeaters.app.controller.exception.DatabaseControllerException;
import com.pickyeaters.app.controller.exception.LoginControllerException;
import com.pickyeaters.app.controller.exception.SettingsControllerException;

public class InitViewController {

    private LoginController loginController = new LoginController();
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
        try {
            SettingsController.getInstance().init(
                    settingsBean.getDatabaseDriver(),
                    settingsBean.getDatabaseHost(),
                    Integer.parseInt(settingsBean.getDatabasePort()),
                    settingsBean.getDatabaseName(),
                    settingsBean.getDatabaseUser(),
                    settingsBean.getDatabasePassword()
            );
        } catch (NumberFormatException ex) {
            throw new SettingsControllerException("Cannot convert port into a number!");
        }

        // try to init database connection
        DatabaseController.getInstance().init();
        // try to save current config on file
        SettingsController.getInstance().persist();
    }

    public void login(LoginBean loginBean) throws LoginControllerException, DatabaseControllerException {
        loginController.login(loginBean.getUsername(), loginBean.getPassword());
    }
}
