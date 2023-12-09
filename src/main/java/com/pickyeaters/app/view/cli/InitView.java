package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.controller.DatabaseController;
import com.pickyeaters.app.controller.SettingsController;
import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.utils.DatabaseControllerException;
import com.pickyeaters.app.utils.SettingsControllerException;

import java.util.Scanner;

public class InitView implements ViewCLI {
    public static void show(String[] args) {
        loadSettings();
        loadDatabase();
        saveSettings();
    }

    private static void loadSettings() {
        // Try to load config from file system
        try {
            SettingsController.init();
        } catch (SettingsControllerException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            // If you cannot load from file system, ask user input
            askConfig();
        }
    }

    private static void loadDatabase() {
        Settings settings = SettingsController.getSettings();
        boolean success = false;
        while(!success) {
            try {
                // try to init database connection
                DatabaseController.init(
                        settings.getDatabaseHost(),
                        settings.getDatabasePort(),
                        settings.getDatabaseName(),
                        settings.getDatabaseUser(),
                        settings.getDatabasePassword());
                // exit if not catch exception
                success = true;
            } catch (DatabaseControllerException ex) {
                System.out.println("ERROR: " + ex.getMessage());
                askConfig();
            }
        }
    }

    private static void saveSettings() {
        // try to save current config on file
        try {
            SettingsController.persist();
        } catch (SettingsControllerException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    private static void askConfig() {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Database Host: ");
        String host = userInput.nextLine();

        System.out.print("Database Port: ");
        int port = -1;
        while (port == -1) {
            try {
                port = Integer.parseInt(userInput.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("ERROR: Please provide a number.");
            }
        }

        System.out.print("Database Name: ");
        String name = userInput.nextLine();
        System.out.print("Database User: ");
        String user = userInput.nextLine();
        System.out.print("Database Password: ");
        String password = userInput.nextLine();

        try {
            SettingsController.init(host, port, name, user, password);
        } catch (SettingsControllerException ex) {
            // Will never throw since args are not null!
        }
    }

}
