package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.SettingsDatabase;
import com.pickyeaters.logic.utils.QueryProcedure;
import com.pickyeaters.logic.utils.QueryResultSet;

import java.sql.*;

public class DatabaseController {
    private static final DatabaseController instance = new DatabaseController();

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

    public QueryProcedure queryProcedure(String query) throws DatabaseControllerException {
        if (conn == null) {
            throw new DatabaseControllerException("Connection is not ready");
        }

        try {
            return new QueryProcedure(conn.prepareCall(query), query);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot prepare query");
        }
    }

    public QueryResultSet queryResultSet(String query) throws DatabaseControllerException {
        if (conn == null) {
            throw new DatabaseControllerException("Connection is not ready");
        }

        try {
            return new QueryResultSet(conn.prepareCall(query), query);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot prepare query");
        }

    }
}
