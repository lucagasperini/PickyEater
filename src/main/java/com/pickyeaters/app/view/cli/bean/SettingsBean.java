package com.pickyeaters.app.view.cli.bean;

public class SettingsBean {
    private String host;
    private String port;
    private String name;
    private String user;
    private String password;

    public SettingsBean(String host, String port, String name, String user, String password) {
        this.host = host;
        this.port = port;
        this.name = name;
        this.user = user;
        this.password = password;
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

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }
}
