package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.Controller;
import com.pickyeaters.logic.controller.LoginController;
import com.pickyeaters.logic.controller.RegistrationController;

import java.util.Scanner;

public class StartView {
    private String welcomeText;
    private String signInText;
    private String signUpText;
    private String actionsText;
    private String chooseAnActionText;
    private String quitText;
    private Controller controller;

    private void start(){
        show();
        requestLoop();
    }
    private void show(){
        System.out.println("PICKY EATERS");
        System.out.printf("%s%n", welcomeText);
        System.out.println("-------------------------");
        System.out.printf("%s%n", actionsText);
        System.out.printf("1) %s%n", signInText);
        System.out.printf("2) %s%n", signUpText);
        System.out.printf("3) %s%n", quitText);
        System.out.printf("%s: ", chooseAnActionText);
    }

    private void requestLoop() {
        Scanner userInput = new Scanner(System.in);
        while(controller.isRunning()) {
            System.out.print("> ");
            try {
                request(userInput.nextLine());
            } catch (UnsupportedOperationException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }

    public void request(String request) {
        String[] tmp = request.split(" ");
        switch (tmp[0].toLowerCase()) {
            case "1":
                controller = new LoginController();
                controller.start();
                break;
            case "2":
                controller = new RegistrationController();
                controller.start();
                break;
            case "3":
                System.out.println("ADIOS!!!!");
                //ESCI
                break;
            default:
                throw new UnsupportedOperationException("Cannot execute " + tmp[0].toLowerCase());
        }
    }
}
