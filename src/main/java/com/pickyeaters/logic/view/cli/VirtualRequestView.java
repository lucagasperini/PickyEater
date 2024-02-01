package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.ViewInterface;

import java.util.Map;
import java.util.Scanner;

public abstract class VirtualRequestView extends VirtualViewCLI {

    private String viewName;
    protected VirtualRequestView(String viewName) {
        this.viewName = viewName;
    }

    protected abstract boolean request(String request);

    protected abstract String requestHelp();


    protected void requestLoop() {
        Scanner userInput = new Scanner(System.in);
        while(true) {
            System.out.print(viewName + "> ");
            String input = userInput.nextLine().toLowerCase();
            switch (input) {
                case "b", "back" -> {
                    return;
                }
                case "q", "quit" -> System.exit(0);
                case "h", "help" -> System.out.println(requestHelp());
                default -> {
                    if (!request(input)) {
                        showError("UNSUPPORTED_OPERATION");
                    }
                }
            }
        }
    }
}
