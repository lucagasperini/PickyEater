package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.Main;
import com.pickyeaters.app.controller.SessionController;
import com.pickyeaters.app.controller.SettingsController;
import com.pickyeaters.app.controller.DatabaseController;
import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.model.Settings;
import com.pickyeaters.app.utils.DatabaseControllerException;
import com.pickyeaters.app.utils.SettingsControllerException;
import com.pickyeaters.app.view.controller.MainViewController;

import java.util.Scanner;

public class MainCLI {

    private MainViewController controller = new MainViewController();
    public void start() {
        controller.start();
        System.out.println("Welcome to PickyEaters");
        requestLoop();
        System.out.println("Goodbye!");
    }

    private void requestLoop() {
        Scanner userInput = new Scanner(System.in);
        while(controller.isRunning()) {
            System.out.print("> ");
            try {
                controller.request(userInput.nextLine());
            } catch (UnsupportedOperationException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }
}
