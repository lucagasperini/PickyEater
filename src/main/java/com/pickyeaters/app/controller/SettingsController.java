package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.utils.OS;
import com.pickyeaters.app.utils.SettingsControllerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsController {
    private static Settings settings = null;
    public static void init() throws SettingsControllerException {
        try {
            loadConfig(OS.getConfigFilePath());
            validate();
        } catch (SettingsControllerException ex) {
            throw ex;
        }
    }

    public static void init(
            String databaseUrl,
            String databaseName,
            String databaseUser,
            String databasePassword) throws SettingsControllerException {
        settings = new Settings();

        settings.setDatabaseUrl(databaseUrl);
        settings.setDatabaseName(databaseName);
        settings.setDatabaseUser(databaseUser);
        settings.setDatabasePassword(databasePassword);

        try {
            validate();
        } catch (SettingsControllerException ex) {
            throw ex;
        }
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

    private static void loadProperties(Properties prop) {
        settings = new Settings();

        settings.setDatabaseUrl(prop.getProperty("database.url"));
        settings.setDatabaseName(prop.getProperty("database.name"));
        settings.setDatabaseUser(prop.getProperty("database.user"));
        settings.setDatabasePassword(prop.getProperty("database.password"));
    }

    private static void validate() throws SettingsControllerException {
        if(settings == null) {
            throw new SettingsControllerException("Settings not load");
        }

        if(settings.getDatabaseUrl() == null) {
            throw new SettingsControllerException("Database URL not load");
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

    private static void saveConfig(String configFile) {

    }

}
