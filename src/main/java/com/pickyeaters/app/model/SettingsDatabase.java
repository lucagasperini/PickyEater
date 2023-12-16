package com.pickyeaters.app.model;

public class SettingsDatabase {
    private String driver = null;
    private String host = null;
    private int port = -1;
    private String password = null;
    private String name = null;
    private String user = null;
    public SettingsDatabase() {

    }

    public SettingsDatabase(String driver, String host, int port, String name, String user, String password) {
        this.driver = driver;
        this.host = host;
        this.port = port;
        this.name = name;
        this.user = user;
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public String getDriver() {
        return driver;
    }

    public String getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
