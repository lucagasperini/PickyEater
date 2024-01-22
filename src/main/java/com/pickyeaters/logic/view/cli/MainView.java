package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.graphics.MainViewController;

import java.util.Scanner;

public class MainView {

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
