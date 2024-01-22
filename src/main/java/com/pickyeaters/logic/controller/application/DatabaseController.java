package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.model.Session;
import com.pickyeaters.logic.model.SettingsDatabase;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;

import java.sql.*;

public class DatabaseController {
    private static DatabaseController instance = new DatabaseController();

    public static DatabaseController getInstance() {
        return instance;
    }

    private DatabaseController() {

    }

    private Connection conn = null;
    public void init() throws DatabaseControllerException {
        // validation of settings are performed on SettingsDatabaseController
        SettingsDatabase settings = SettingsController.getInstance().getSettings().getDatabase();
        String url = formatURL(
                settings.getDriver(),
                settings.getHost(),
                settings.getPort(),
                settings.getName());

        connect(url, settings.getUser(), settings.getPassword());
    }
    private String formatURL(String driver, String host, int port, String name) {
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
