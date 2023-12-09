package com.pickyeaters.app.model;

public class Settings {
    private String databaseHost = null;
    private int databasePort = -1;
    private String databasePassword = null;
    private String databaseName = null;
    private String databaseUser = null;

    public Settings() {

    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public void setDatabaseHost(String databaseUrl) {
        this.databaseHost = databaseUrl;
    }

    public void setDatabasePort(int databasePort) {
        this.databasePort = databasePort;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }
}
