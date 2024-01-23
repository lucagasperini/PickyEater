package com.pickyeaters.logic.controller.application;

import com.pickyeaters.app.view.bean.SettingsBean;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.model.SettingsDatabase;
import com.pickyeaters.logic.model.SettingsLocale;

import java.util.Properties;
public class SettingsLocaleController {

    private SettingsLocale settings = new SettingsLocale();

    private static final String[] LANG_LIST = {"en", "it"};
    private static final String DEFAULT_LANG = "it";

    public SettingsLocaleController() {
    }

    public SettingsLocale getSettings() {
        return settings;
    }

    public void load(Properties prop) throws SettingsControllerException {
        String lang = prop.getProperty("locale.lang");

        settings.setLang(lang);
    }

    public void load(SettingsBean settingsBean) {
        settings.setLang(settingsBean.getLocaleLang());
    }

    public void validate() throws SettingsControllerException {
        if(settings == null) {
            throw new SettingsControllerException("SettingsLocale not load");
        }

        if(!validateLang()) {
            settings.setLang(DEFAULT_LANG);
        }

    }

    private boolean validateLang() {
        for(String str : LANG_LIST) {
            if(str.equals(settings.getLang())) {
                return true;
            }
        }
        return false;
    }

    public void save(Properties prop) throws SettingsControllerException {
        validate();

        prop.setProperty("locale.lang", settings.getLang());
    }
}