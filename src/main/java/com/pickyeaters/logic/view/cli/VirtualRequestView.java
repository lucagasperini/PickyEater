package com.pickyeaters.logic.view.cli;

import java.util.Scanner;

public abstract class VirtualRequestView extends VirtualViewCLI {

    private final String viewName;
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
                case "h", "help" -> System.out.println("""
                                [back, b]
                                [quit, q]
                                """ +
                        requestHelp()
                );
                default -> {
                    if (!request(input)) {
                        showError("UNSUPPORTED_OPERATION");
                    }
                }
            }
        }
    }
}
