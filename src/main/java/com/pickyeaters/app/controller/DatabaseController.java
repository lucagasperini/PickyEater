package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.model.SettingsDatabase;
import com.pickyeaters.app.utils.DatabaseControllerException;

import java.sql.*;

public class DatabaseController {
    private final int MIN_PORT_NUMBER = 0;
    private final int MAX_PORT_NUMBER = 65535;
    private final String DEFAULT_DRIVER = "postgresql";
    private static DatabaseController instance = new DatabaseController();

    public static DatabaseController getInstance() {
        return instance;
    }

    private DatabaseController() {

    }

    private Connection conn = null;
    public void init() throws DatabaseControllerException {
        SettingsDatabase settings = SettingsController.getInstance().getSettings().getDatabase();
        String url = formatURL(
                settings.getDriver(),
                settings.getHost(),
                settings.getPort(),
                settings.getName());

        connect(url, settings.getUser(), settings.getPassword());
    }
    private String formatURL(String driver, String host, int port, String name) throws DatabaseControllerException {
        if(driver == null || driver.isEmpty()) {
            // TODO: Not sure if default value is better on SettingsController
            driver = DEFAULT_DRIVER;
        }
        if(host == null || host.isEmpty()) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Host invalid.");
        }
        if(port <= MIN_PORT_NUMBER || port >= MAX_PORT_NUMBER) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Port invalid.");
        }
        if(name == null || name.isEmpty()) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Name invalid.");
        }
        return "jdbc:" + driver + "://" + host + ":" + port + "/" + name;
    }
    private void connect(String url, String user, String password) throws DatabaseControllerException {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot connect to database: " + url);
        }
    }

    public Session login(String username, String password) throws DatabaseControllerException {
        if(conn == null) {
            throw new DatabaseControllerException("Connection is not ready.");
        }
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("CALL login(?,?,?)");
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.setNull(3, Types.VARCHAR);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot create a login call.");
        }

        try {
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot execute a login call.");
        }

        String token = null;
        try {
            token = cs.getString(3);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot get response from login call.");
        }
        if(token == null) {
            return new Session();
        } else {
            return new Session(token);
        }
    }

}
