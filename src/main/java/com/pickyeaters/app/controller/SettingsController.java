package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.utils.OS;
import com.pickyeaters.app.utils.SettingsControllerException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsController {
    private static Settings settings = null;
    public static void init() throws SettingsControllerException {
        loadConfig(OS.getConfigFilePath());
        validate();
    }

    public static void init(
            String databaseHost,
            int databasePort,
            String databaseName,
            String databaseUser,
            String databasePassword) throws SettingsControllerException {
        settings = new Settings();

        settings.setDatabaseHost(databaseHost);
        settings.setDatabasePort(databasePort);
        settings.setDatabaseName(databaseName);
        settings.setDatabaseUser(databaseUser);
        settings.setDatabasePassword(databasePassword);

        validate();
    }

    public static Settings getSettings() {
        return settings;
    }

    private static void loadConfig(String configFile) throws SettingsControllerException {
        Properties prop = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(configFile);
        } catch (IOException ex) {
            throw new SettingsControllerException("Cannot read config file: " + configFile);
        }

        try {
            prop.load(fis);
        } catch (IOException ex) {
            throw new SettingsControllerException("Cannot load config file: " + configFile);
        }

        loadProperties(prop);
    }

    private static void loadProperties(Properties prop) throws SettingsControllerException {
        settings = new Settings();

        settings.setDatabaseHost(prop.getProperty("database.host"));
        try {
            settings.setDatabasePort(Integer.parseInt(prop.getProperty("database.port")));
        } catch (NumberFormatException ex) {
            throw new SettingsControllerException("Cannot read port from config");
        }
        settings.setDatabaseName(prop.getProperty("database.name"));
        settings.setDatabaseUser(prop.getProperty("database.user"));
        settings.setDatabasePassword(prop.getProperty("database.password"));
    }

    private static void validate() throws SettingsControllerException {
        if(settings == null) {
            throw new SettingsControllerException("Settings not load");
        }

        if(settings.getDatabaseHost() == null) {
            throw new SettingsControllerException("Database host not load");
        }

        if(settings.getDatabaseName() == null) {
            throw new SettingsControllerException("Database name not load");
        }

        if(settings.getDatabaseUser() == null) {
            throw new SettingsControllerException("Database user not load");
        }

        if(settings.getDatabasePassword() == null) {
            throw new SettingsControllerException("Database password not load");
        }
    }

    public static void persist() throws SettingsControllerException {
        saveConfig(OS.getConfigFilePath());
    }

    private static void saveConfig(String configFile) throws SettingsControllerException {
        Properties prop = new Properties();
        saveProperties(prop);

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(configFile);
        } catch (IOException ex) {
            throw new SettingsControllerException("Cannot write config file: " + configFile);
        }

        try {
            prop.save(fos, null);
        } catch (ClassCastException ex) {
            throw new SettingsControllerException("Cannot save config file: " + configFile);
        }
    }

    private static void saveProperties(Properties prop) throws SettingsControllerException {
        validate();

        prop.setProperty("database.host", settings.getDatabaseHost());
        prop.setProperty("database.port", Integer.toString(settings.getDatabasePort()));
        prop.setProperty("database.name", settings.getDatabaseName());
        prop.setProperty("database.user", settings.getDatabaseUser());
        prop.setProperty("database.password", settings.getDatabasePassword());
    }

}
