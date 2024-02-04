package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.VirtualException;
import com.pickyeaters.logic.view.ViewInterface;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class VirtualViewCLI implements ViewInterface {
    private static MainView mainView;

    public static MainView getMainView() {
        return mainView;
    }

    public static void setMainView(MainView mainView) {
        VirtualViewCLI.mainView = mainView;
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

    public void showError(VirtualException ex) {
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

    public void printFieldList(String key, List<String> value) {
        StringBuilder builder = new StringBuilder();
        for(String i : value) {
            builder.append(i);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(SettingsController.i18n(key) + ": " + builder);
    }

    public void printFieldBoolean(String key, boolean value) {
        String yes = SettingsController.i18n("YES");
        String no = SettingsController.i18n("NO");
        System.out.println(SettingsController.i18n(key) + ": " + (value ? yes : no));
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
