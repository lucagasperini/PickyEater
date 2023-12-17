package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.view.bean.LoginBean;
import com.pickyeaters.app.controller.exception.DatabaseControllerException;
import com.pickyeaters.app.controller.exception.SessionControllerException;
import com.pickyeaters.app.controller.exception.SettingsControllerException;
import com.pickyeaters.app.view.bean.SettingsBean;
import com.pickyeaters.app.view.controller.InitViewController;

import java.util.Scanner;

public class InitView implements ViewCLI {
    private InitViewController controller = new InitViewController();
    @Override
    public void show(String[] args) {
        try {
            controller.loadFromFile();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            // If you cannot load settings/database, ask user input
            askConfig();
        }

        askLogin();
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
            controller.loadFromInput(settings);
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            askConfig();
        }
    }

    private void askLogin() {
        Scanner userInput = new Scanner(System.in);
        LoginBean loginBean = new LoginBean();
        System.out.print("Username: ");
        loginBean.setUsername(userInput.nextLine());
        System.out.print("Password: ");
        loginBean.setPassword(userInput.nextLine());
        try {
            controller.login(loginBean);
            // TODO: Database should be available at this point
        } catch (SessionControllerException | DatabaseControllerException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            askLogin();
        }
    }

}
