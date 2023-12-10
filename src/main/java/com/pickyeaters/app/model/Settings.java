package com.pickyeaters.app.model;

public class Settings {
    SettingsDatabase database = null;

    public Settings() {

    }

    public SettingsDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SettingsDatabase database) {
        this.database = database;
    }
}
