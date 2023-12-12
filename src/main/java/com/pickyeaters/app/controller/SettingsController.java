package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.utils.OS;
import com.pickyeaters.app.utils.SettingsControllerException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingsController {
    private static Settings settings = null;
    private static SettingsDatabaseController databaseController = null;
    public static void init() throws SettingsControllerException {
        databaseController = new SettingsDatabaseController();
        loadConfig(OS.getConfigFilePath());
        validate();
    }
/*
    public static void init(Settings newSettings) throws SettingsControllerException {
        settings = newSettings;
        validate();
    }
*/
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

        databaseController.load(prop);
        settings.setDatabase(databaseController.getSettingsDatabase());
    }

    private static void validate() throws SettingsControllerException {
        if(settings == null) {
            throw new SettingsControllerException("Settings not load");
        }

        databaseController.validate();
    }

    public static void persist() throws SettingsControllerException {
        saveConfig(OS.getConfigFilePath(), OS.getConfigDir());
    }

    private static void saveConfig(String configFile, String configDir) throws SettingsControllerException {
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

    private static void saveProperties(Properties prop) throws SettingsControllerException {
        validate();

        databaseController.save(prop);
    }

}
