package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.controller.SessionController;
import com.pickyeaters.app.controller.SettingsController;
import com.pickyeaters.app.controller.DatabaseController;
import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.utils.DatabaseControllerException;
import com.pickyeaters.app.utils.SettingsControllerException;

import java.util.Scanner;

public class MainCLI {
    private InitView initView = new InitView();
    private HelpView helpView = new HelpView();
    public void start() {
        isRunning = true;
        initView.show(null);
        welcome();
        login();
        requestLoop();
    }

    public void quit() {
        System.out.println("Goodbye!");
        isRunning = false;
    }

    private boolean isRunning = false;
    private Session session = new Session();
    private void login() {
        Scanner userInput = new Scanner(System.in);
        do {
            System.out.print("Username: ");
            String username = userInput.nextLine();
            System.out.print("Password: ");
            String password = userInput.nextLine();

            session = SessionController.login(username, password);
        } while(!session.isValid());
    }
    private void requestLoop() {
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

    private void requestParser(String request) {
        String[] tmp = request.split(" ");
        switch (tmp[0].toLowerCase()) {
            case "help":
            case "h":
                helpView.show(tmp);
                break;
            case "quit":
            case "q":
                quit();
                break;
            default:
                throw new UnsupportedOperationException("Cannot execute " + tmp[0].toLowerCase());
        }
    }

    private void welcome() {
        System.out.println("Welcome to PickyEaters");
    }
}
