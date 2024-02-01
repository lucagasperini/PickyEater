package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.application.MainController;

import java.util.Map;
import java.util.Scanner;

public class MainView extends VirtualViewCLI {

    private MainController controller = new MainController();
    public MainView() {
        init(controller);
    }
    private void requestLoop() {
        Scanner userInput = new Scanner(System.in);
        while(controller.isRunning()) {
            System.out.print("> ");
            try {
                request(userInput.nextLine());
            } catch (UnsupportedOperationException ex) {
                showError("UNSUPPORTED_OPERATION");
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
            case "ingredient":
            case "i":
                break;
            case "quit":
            case "q":
                controller.quit();
                break;
            default:
                throw new UnsupportedOperationException("Cannot execute " + tmp[0].toLowerCase());
        }
    }

    @Override
    public void show(Map<String, String> arg) {
        controller.start();
        InitView initView = new InitView(controller.getInit());
        initView.show();
        System.out.println("Welcome to PickyEaters");
        requestLoop();
        System.out.println("Goodbye!");
    }
}
