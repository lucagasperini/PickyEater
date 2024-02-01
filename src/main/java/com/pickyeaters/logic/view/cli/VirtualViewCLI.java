package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.ViewInterface;

import java.util.Map;

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
            System.out.println(title);
        }
        if(!header.isEmpty()) {
            System.out.println(header);
        }
        if(!content.isEmpty()) {
            System.out.println(content);
        }
    }

    public void showError(ControllerException ex) {
        showError(ex.getKey());
    }

    public abstract void show(Map<String, String> arg);
    public void show() {
        show(null);
    }
}
