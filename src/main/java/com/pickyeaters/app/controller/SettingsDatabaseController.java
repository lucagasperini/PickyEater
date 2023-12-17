package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.model.SettingsDatabase;
import com.pickyeaters.app.utils.DatabaseControllerException;
import com.pickyeaters.app.utils.SettingsControllerException;

import java.util.Arrays;
import java.util.Properties;

public class SettingsDatabaseController {

    private SettingsDatabase settingsDatabase = new SettingsDatabase();
    private final int MIN_PORT_NUMBER = 0;
    private final int MAX_PORT_NUMBER = 65535;

    private static final String[] DRIVER_LIST = {"postgresql"/*, "mysql"*/};

    public SettingsDatabaseController() {
    }

    public SettingsDatabase getSettingsDatabase() {
        return settingsDatabase;
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

        settingsDatabase = new SettingsDatabase(driver, host, port, name, user, password);
    }

    public void load(String driver, String host, String port, String name, String user, String password) throws SettingsControllerException {
        try {
            int portNumber = Integer.parseInt(port);
            settingsDatabase = new SettingsDatabase(driver, host, portNumber, name, user, password);
        } catch (NumberFormatException ex) {
            throw new SettingsControllerException("Cannot convert port into a number!");
        }
    }

    public void validate() throws SettingsControllerException {
        if(settingsDatabase == null) {
            throw new SettingsControllerException("SettingsDatabase not load");
        }

        if(!validateDriver()) {
            throw new SettingsControllerException("Database driver not load or invalid");
        }

        if(settingsDatabase.getHost() == null) {
            throw new SettingsControllerException("Database host not load");
        }

        if(settingsDatabase.getPort() <= MIN_PORT_NUMBER || settingsDatabase.getPort() >= MAX_PORT_NUMBER) {
            throw new SettingsControllerException("Database port not load.");
        }

        if(settingsDatabase.getName() == null) {
            throw new SettingsControllerException("Database name not load");
        }

        if(settingsDatabase.getUser() == null) {
            throw new SettingsControllerException("Database user not load");
        }

        if(settingsDatabase.getPassword() == null) {
            throw new SettingsControllerException("Database password not load");
        }
    }

    private boolean validateDriver() {
        for(String str : DRIVER_LIST) {
            if(str.equals(getSettingsDatabase().getDriver())) {
                return true;
            }
        }
        return false;
    }

    public void save(Properties prop) throws SettingsControllerException {
        validate();

        prop.setProperty("database.driver", settingsDatabase.getDriver());
        prop.setProperty("database.host", settingsDatabase.getHost());
        prop.setProperty("database.port", Integer.toString(settingsDatabase.getPort()));
        prop.setProperty("database.name", settingsDatabase.getName());
        prop.setProperty("database.user", settingsDatabase.getUser());
        prop.setProperty("database.password", settingsDatabase.getPassword());
    }
}
