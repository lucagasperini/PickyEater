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

        System.out.println(SettingsController.i18n(key + "_ALERT_ERROR_TITLE"));
        System.out.println(SettingsController.i18n(key + "_ALERT_ERROR_HEADER"));
        System.out.println(SettingsController.i18n(key + "_ALERT_ERROR_CONTENT"));
    }

    public void showError(ControllerException ex) {
        showError(ex.getKey());
    }

    public abstract void show(Map<String, String> arg);
    public void show() {
        show(null);
    }
}
