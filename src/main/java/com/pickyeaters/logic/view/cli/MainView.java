package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.MainController;

import java.util.Scanner;

public class MainView {

    private MainController controller = new MainController();
    public void start() {
        controller.start();
        InitView initView = new InitView(controller);
        initView.show();
        System.out.println("Welcome to PickyEaters");
        requestLoop();
        System.out.println("Goodbye!");
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
            case "help":
            case "h":
                HelpView helpView = new HelpView();
                helpView.show();
                break;
            case "1":
                //IngredientView ingredientView = new IngredientView();
                //ingredientView.show(tmp);
                break;
            case "quit":
            case "q":
                controller.quit();
                break;
            default:
                throw new UnsupportedOperationException("Cannot execute " + tmp[0].toLowerCase());
        }
    }

}
