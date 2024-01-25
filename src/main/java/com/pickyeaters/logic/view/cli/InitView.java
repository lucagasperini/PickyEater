package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.view.VirtualView;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.SettingsBean;

import java.util.Scanner;

public class InitView extends VirtualView {
    public InitView(MainController controller) {
        super(controller);
    }

    public void show() {
        try {
            controller.getInitController().loadFromFile();
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
            controller.getInitController().loadFromInput(settings);
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
            controller.getLoginController().login(loginBean);
        } catch (LoginControllerException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            askLogin();
        }
    }

}
