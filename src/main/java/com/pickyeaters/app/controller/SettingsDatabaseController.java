package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.model.SettingsDatabase;
import com.pickyeaters.app.utils.SettingsControllerException;

import java.util.Properties;

public class SettingsDatabaseController {

    private SettingsDatabase settingsDatabase = null;

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

    public void load(SettingsDatabase newSettings) {
        settingsDatabase = newSettings;
    }

    public void validate() throws SettingsControllerException {
        if(settingsDatabase == null) {
            throw new SettingsControllerException("SettingsDatabase not load");
        }

        if(settingsDatabase.getHost() == null) {
            throw new SettingsControllerException("Database host not load");
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
