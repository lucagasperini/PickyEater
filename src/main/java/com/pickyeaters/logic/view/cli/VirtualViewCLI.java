package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.VirtualException;
import com.pickyeaters.logic.view.VirtualView;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class VirtualViewCLI extends VirtualView {
    private static MainView mainView;

    protected VirtualViewCLI(String resource) {
        super(resource);
    }

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
        String title = i18nGlobal(key + "_ALERT_ERROR_TITLE");
        String header = i18nGlobal(key + "_ALERT_ERROR_HEADER");
        String content = i18nGlobal(key + "_ALERT_ERROR_CONTENT");
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
        System.out.println(i18n(key) + ": " + value);
    }

    public void printFieldList(String key, List<String> value) {
        StringBuilder builder = new StringBuilder();
        for(String i : value) {
            builder.append(i);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(i18n(key) + ": " + builder);
    }

    public void printFieldBoolean(String key, boolean value) {
        String yes = i18nGlobal("YES");
        String no = i18nGlobal("NO");
        System.out.println(i18n(key) + ": " + (value ? yes : no));
    }

    public String askField(String key, String value) {
        Scanner userInput = new Scanner(System.in);
        System.out.print(i18n(key) + " [" + value + "]: ");
        String out = userInput.nextLine();
        if(out.isEmpty()) {
            return value;
        } else {
            return out;
        }
    }
}
