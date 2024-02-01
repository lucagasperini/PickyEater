package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.ViewInterface;

import java.util.Map;
import java.util.Scanner;

public abstract class VirtualViewCLI implements ViewInterface {

    private static MainController mainController;

    protected static void init(MainController mainController) {
        VirtualViewCLI.mainController = mainController;
    }

    protected static MainController getMainController() {
        return mainController;
    }

    public void showError(String key) {
        if(key.isEmpty()) {
            key = "DEFAULT";
        }
        String title = SettingsController.i18n(key + "_ALERT_ERROR_TITLE");
        String header = SettingsController.i18n(key + "_ALERT_ERROR_HEADER");
        String content = SettingsController.i18n(key + "_ALERT_ERROR_CONTENT");
        if(!title.isEmpty()) {
            print(title);
        }
        if(!header.isEmpty()) {
            print(header);
        }
        if(!content.isEmpty()) {
            print(content);
        }
    }

    public void showError(ControllerException ex) {
        showError(ex.getKey());
    }

    public abstract void show(Map<String, String> arg);
    public void show() {
        show(null);
    }

    public void print(String text) {
        System.out.println(text);
    }

    public void printField(String key, String value) {
        System.out.println(SettingsController.i18n(key) + ": " + value);
    }

    public String askField(String key, String value) {
        Scanner userInput = new Scanner(System.in);
        System.out.print(SettingsController.i18n(key) + " [" + value + "]: ");
        String out = userInput.nextLine();
        if(out.isEmpty()) {
            return value;
        } else {
            return out;
        }
    }
}
