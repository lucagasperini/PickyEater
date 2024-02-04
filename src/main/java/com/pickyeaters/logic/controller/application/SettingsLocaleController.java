package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.model.SettingsLocale;
import com.pickyeaters.logic.view.bean.SettingsBean;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsLocaleController implements SettingsVirtualController {

    private final SettingsLocale settings = new SettingsLocale();

    private static final String[] LANG_LIST = {"en", "it"};
    private static final String DEFAULT_LANG = "it";
    private final Properties properties = new Properties();
    private ResourceBundle i18nBundle = null;

    public SettingsLocale getSettings() {
        return settings;
    }

    public void load(Properties prop) {
        String lang = prop.getProperty("locale.lang");

        settings.setLang(lang);
    }

    public void load(SettingsBean settingsBean) {
        settings.setLang(settingsBean.getLocaleLang());
    }

    public void validate() throws SettingsControllerException {
        if(!validateLang()) {
            settings.setLang(DEFAULT_LANG);
        }

        loadBundle();
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

    private void loadBundle() throws SettingsControllerException {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("i18n.properties"));
        } catch (IOException e) {
            throw new SettingsControllerException("Unable to load property file");
        }
        i18nBundle = ResourceBundle.getBundle("i18n", new Locale(settings.getLang()));
    }
    public String i18n(String key) {
        try {
            if(i18nBundle == null) {
                return "#" + key + "#";
            }
            return i18nBundle.getString(key);
        } catch (MissingResourceException e) {
            return "[" + key + "]";
        }
    }
}