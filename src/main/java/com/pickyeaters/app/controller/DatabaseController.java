package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.utils.DatabaseControllerException;

import java.sql.*;

public class DatabaseController {
    //TODO: Use Settings!!!
    private static final String url = "jdbc:postgresql://localhost:5432/picky";
    private static final String user = "picky";
    private static final String password = "picky";

    private static Connection conn = null;
    public static void init() throws DatabaseControllerException {
        connect();
    }
    private static void connect() throws DatabaseControllerException {
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
