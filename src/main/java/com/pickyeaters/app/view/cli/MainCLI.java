package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.controller.SessionController;
import com.pickyeaters.app.controller.SettingsController;
import com.pickyeaters.app.controller.DatabaseController;
import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.utils.DatabaseControllerException;
import com.pickyeaters.app.utils.SettingsControllerException;

import java.util.Scanner;

public class MainCLI {
    public static void start() {
        load();
        welcome();
        login();
        requestLoop();
    }

    public static void quit() {
        System.out.println("Goodbye!");
        isRunning = false;
    }

    private static boolean isRunning = false;
    private static Session session = new Session();
    private static void load() {
        isRunning = true;

        // Try to load config from file system
        try {
            SettingsController.init();
        } catch (SettingsControllerException ex) {
            // If you cannot load from file system, ask user input
            askDatabase();
        }
        try {
            DatabaseController.init();
        } catch (DatabaseControllerException ex) {
            // TODO: Code this!!!
        }
    }
    private static void askDatabase() {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Database URL: ");
        String url = userInput.nextLine();
        System.out.print("Database Name: ");
        String name = userInput.nextLine();
        System.out.print("Database User: ");
        String user = userInput.nextLine();
        System.out.print("Database Password: ");
        String password = userInput.nextLine();
        try {
            SettingsController.init(url, name, user, password);
        } catch (SettingsControllerException ex) {
            // Will never throw since args are not null!
        }
    }
    private static void login() {
        Scanner userInput = new Scanner(System.in);
        do {
            System.out.print("Username: ");
            String username = userInput.nextLine();
            System.out.print("Password: ");
            String password = userInput.nextLine();

            session = SessionController.login(username, password);
        } while(!session.isValid());
    }
    private static void requestLoop() {
        Scanner userInput = new Scanner(System.in);
        while(isRunning) {
            System.out.print("> ");
            try {
                requestParser(userInput.nextLine());
            } catch (UnsupportedOperationException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }

    private static void requestParser(String request) {
        String[] tmp = request.split(" ");
        switch (tmp[0].toLowerCase()) {
            case "help":
            case "h":
                HelpView.show(tmp);
                break;
            case "quit":
            case "q":
                quit();
                break;
            default:
                throw new UnsupportedOperationException("Cannot execute " + tmp[0].toLowerCase());
        }
    }

    private static void welcome() {
        System.out.println("Welcome to PickyEaters");
    }
}
