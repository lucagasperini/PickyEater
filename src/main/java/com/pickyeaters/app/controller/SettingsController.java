package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.utils.OS;
import com.pickyeaters.app.controller.exception.SettingsControllerException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingsController {
    private Settings settings = new Settings();
    private SettingsDatabaseController databaseController = new SettingsDatabaseController();
    private static SettingsController instance = new SettingsController();

    public static SettingsController getInstance() {
        return instance;
    }

    private SettingsController() {

    }

    public void init() throws SettingsControllerException {
        loadConfig(OS.getConfigFilePath());
        validate();
    }

    public void init(String databaseDriver,
                     String databaseHost,
                     int databasePort,
                     String databaseName,
                     String databaseUser,
                     String databasePassword)
            throws SettingsControllerException {
        databaseController.load(
                databaseDriver,
                databaseHost,
                databasePort,
                databaseName,
                databaseUser,
                databasePassword
        );
        settings.setDatabase(databaseController.getSettingsDatabase());
        validate();
    }

    public Settings getSettings() {
        return settings;
    }

    private void loadConfig(String configFile) throws SettingsControllerException {
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

    private void loadProperties(Properties prop) throws SettingsControllerException {
        databaseController.load(prop);
        settings.setDatabase(databaseController.getSettingsDatabase());
    }

    private void validate() throws SettingsControllerException {
        if(settings == null) {
            throw new SettingsControllerException("Settings not load");
        }

        databaseController.validate();
    }

    public void persist() throws SettingsControllerException {
        saveConfig(OS.getConfigFilePath(), OS.getConfigDir());
    }

    private void saveConfig(String configFile, String configDir) throws SettingsControllerException {
        // TODO: Should we check for coherent configFile and configDir?
        try {
            Files.createDirectories(Paths.get(configDir));
        } catch (IOException ex) {
            throw new SettingsControllerException("Cannot create config directory: " + configDir);
        }

        Properties prop = new Properties();
        saveProperties(prop);

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(configFile);
        } catch (IOException ex) {
            throw new SettingsControllerException("Cannot write config file: " + configFile);
        }

        try {
            prop.store(fos, null);
        } catch (IOException e) {
            throw new SettingsControllerException("Cannot save config file: " + configFile);
        }
    }

    private void saveProperties(Properties prop) throws SettingsControllerException {
        validate();

        databaseController.save(prop);
    }

}
