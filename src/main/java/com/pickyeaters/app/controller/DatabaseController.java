package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.utils.DatabaseControllerException;

import java.sql.*;

public class DatabaseController {
    private static final int MIN_PORT_NUMBER = 0;
    private static final int MAX_PORT_NUMBER = 65535;

    private static Connection conn = null;
    public static void init(String host, int port, String name, String user, String password) throws DatabaseControllerException {
        String url = formatURL(host, port, name);
        connect(url, user, password);
    }
    private static String formatURL(String host, int port, String name) throws DatabaseControllerException {
        if(host == null || host.isEmpty()) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Host invalid.");
        }
        if(port <= MIN_PORT_NUMBER || port >= MAX_PORT_NUMBER) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Port invalid.");
        }
        if(name == null || name.isEmpty()) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Name invalid.");
        }
        return "jdbc:postgresql://" + host + ":" + port + "/" + name;
    }
    private static void connect(String url, String user, String password) throws DatabaseControllerException {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot connect to database: " + url);
        }
    }

    public static Session login(String username, String password) {
        try {
            // TODO: What if connection is not ready?
            CallableStatement cs = conn.prepareCall("CALL login(?,?,?)");
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.setNull(3, Types.VARCHAR);

            cs.executeUpdate();

            String token = cs.getString(3);
            return new Session(token);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
