package com.pickyeaters.app.view.cli;

import java.util.Scanner;

public class MainCLI {
    public static void start() {
        isRunning = true;
        System.out.println("Welcome to PickyEaters");
        requestLoop();
    }

    public static void quit() {
        System.out.println("Goodbye!");
        isRunning = false;
    }

    private static boolean isRunning = false;
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
}
