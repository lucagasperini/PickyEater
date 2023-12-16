package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.controller.DatabaseController;
import com.pickyeaters.app.controller.SettingsController;
import com.pickyeaters.app.utils.DatabaseControllerException;
import com.pickyeaters.app.utils.SettingsControllerException;
import com.pickyeaters.app.bean.SettingsBean;

import java.util.Scanner;

public class InitView implements ViewCLI {
    @Override
    public void show(String[] args) {
        loadSettings();
        loadDatabase();
        saveSettings();
    }

    private void loadSettings() {
        // Try to load config from file system
        try {
            SettingsController.getInstance().init();
        } catch (SettingsControllerException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            // If you cannot load from file system, ask user input
            askConfig();
        }
    }

    private void loadDatabase() {
        boolean success = false;
        while(!success) {
            try {
                // try to init database connection
                DatabaseController.getInstance().init();
                // exit if not catch exception
                success = true;
            } catch (DatabaseControllerException ex) {
                System.out.println("ERROR: " + ex.getMessage());
                askConfig();
            }
        }
    }

    private void saveSettings() {
        // try to save current config on file
        try {
            SettingsController.getInstance().persist();
        } catch (SettingsControllerException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    private void askConfig() {
        Scanner userInput = new Scanner(System.in);
        SettingsBean settings = new SettingsBean();
        // NOTE: Forcing this driver
        settings.setDatabaseDriver("postgresql");

        System.out.print("Database Host: ");
        settings.setDatabaseHost(userInput.nextLine());
        System.out.print("Database Port: ");
        settings.setDatabasePort(userInput.nextLine());
        System.out.print("Database Name: ");
        settings.setDatabaseName(userInput.nextLine());
        System.out.print("Database User: ");
        settings.setDatabaseUser(userInput.nextLine());
        System.out.print("Database Password: ");
        settings.setDatabasePassword(userInput.nextLine());

        try {
            SettingsController.getInstance().init(settings);
        } catch (SettingsControllerException ex) {
            askConfig();
        }
    }

}
