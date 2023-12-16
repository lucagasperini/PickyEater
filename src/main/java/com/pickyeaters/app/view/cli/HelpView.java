package com.pickyeaters.app.view.cli;

public class HelpView implements ViewCLI {

    public void show(String[] args) {
        System.out.println("List of supported commands:");
        System.out.println("help/h - Show this help message");
        System.out.println("quit/q - Close this program");
    }
}
