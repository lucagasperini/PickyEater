package com.pickyeaters.logic.model;

public class Settings {
    SettingsDatabase database = null;
    SettingsLocale locale = null;

    public Settings() {

    }

    public SettingsDatabase getDatabase() {
        return database;
    }

    public SettingsLocale getLocale() {
        return locale;
    }

    public void setDatabase(SettingsDatabase database) {
        this.database = database;
    }

    public void setLocale(SettingsLocale locale) {
        this.locale = locale;
    }
}
