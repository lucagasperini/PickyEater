package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.model.SettingsDatabase;
import com.pickyeaters.logic.view.bean.SettingsBean;

import java.util.Properties;

public class SettingsDatabaseController implements SettingsVirtualController {

    private SettingsDatabase settings = new SettingsDatabase();
    private final int MIN_PORT_NUMBER = 0;
    private final int MAX_PORT_NUMBER = 65535;

    private static final String[] DRIVER_LIST = {"postgresql"/*, "mysql"*/};
    private static final String DEFAULT_DRIVER = "postgresql";

    public SettingsDatabaseController() {
    }

    public SettingsDatabase getSettings() {
        return settings;
    }

    public void load(Properties prop) throws SettingsControllerException {
        String driver = prop.getProperty("database.driver");
        String host = prop.getProperty("database.host");
        int port = -1;
        try {
            port = Integer.parseInt(prop.getProperty("database.port"));
        } catch (NumberFormatException ex) {
            throw new SettingsControllerException("Cannot read port from config");
        }
        String name = prop.getProperty("database.name");
        String user = prop.getProperty("database.user");
        String password = prop.getProperty("database.password");

        settings = new SettingsDatabase(driver, host, port, name, user, password);
    }

    public void load(SettingsBean settingsBean) throws SettingsControllerException {
        try {
        settings = new SettingsDatabase(settingsBean.getDatabaseDriver(),
                settingsBean.getDatabaseHost(),
                Integer.parseInt(settingsBean.getDatabasePort()),
                settingsBean.getDatabaseName(),
                settingsBean.getDatabaseUser(),
                settingsBean.getDatabasePassword());
        } catch (NumberFormatException ex) {
            throw new SettingsControllerException("Cannot convert port into a number!");
        }
    }

    public void validate() throws SettingsControllerException {
        if(settings == null) {
            throw new SettingsControllerException("SettingsDatabase not load");
        }

        if(!validateDriver()) {
            settings.setDriver(DEFAULT_DRIVER);
        }

        if(settings.getHost() == null) {
            throw new SettingsControllerException("Database host not load");
        }

        if(settings.getPort() <= MIN_PORT_NUMBER || settings.getPort() >= MAX_PORT_NUMBER) {
            throw new SettingsControllerException("Database port not load.");
        }

        if(settings.getName() == null) {
            throw new SettingsControllerException("Database name not load");
        }

        if(settings.getUser() == null) {
            throw new SettingsControllerException("Database user not load");
        }

        if(settings.getPassword() == null) {
            throw new SettingsControllerException("Database password not load");
        }
    }

    private boolean validateDriver() {
        for(String str : DRIVER_LIST) {
            if(str.equals(getSettings().getDriver())) {
                return true;
            }
        }
        return false;
    }

    public void save(Properties prop) throws SettingsControllerException {
        validate();

        prop.setProperty("database.driver", settings.getDriver());
        prop.setProperty("database.host", settings.getHost());
        prop.setProperty("database.port", Integer.toString(settings.getPort()));
        prop.setProperty("database.name", settings.getName());
        prop.setProperty("database.user", settings.getUser());
        prop.setProperty("database.password", settings.getPassword());
    }
}
