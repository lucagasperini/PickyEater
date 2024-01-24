package com.pickyeaters.logic.view.bean;

public class SettingsBean {
    private String databaseDriver;
    private String databaseHost;
    private String databasePort;
    private String databaseName;
    private String databaseUser;
    private String databasePassword;
    private String localeLang;

    public SettingsBean() {
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getLocaleLang() {
        return localeLang;
    }

    public void setDatabaseDriver(String databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public void setLocaleLang(String localeLang) {
        this.localeLang = localeLang;
    }
}
