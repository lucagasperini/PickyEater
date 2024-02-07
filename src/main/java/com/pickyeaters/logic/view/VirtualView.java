package com.pickyeaters.logic.view;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.VirtualException;

public abstract class VirtualView {
    private final String resource;
    protected VirtualView(String resource) {
        this.resource = resource.toUpperCase();
    }
    protected abstract void showError(VirtualException ex);
    protected abstract void showError(String key);

    protected String i18n(String text) {
        if(resource.isEmpty()) {
            return i18nGlobal(text);
        } else {
            return SettingsController.i18n(resource + "_" + text.toUpperCase());
        }
    }

    protected String i18nGlobal(String text) {
        return SettingsController.i18n(text.toUpperCase());
    }
}
