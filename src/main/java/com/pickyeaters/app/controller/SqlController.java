package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;

import java.sql.*;
import java.util.Optional;

public class SqlController {
    //TODO: Use Settings!!!
    private static final String url = "jdbc:postgresql://localhost:5432/picky";
    private static final String user = "picky";
    private static final String password = "picky";

    private static Optional<Connection> connection = Optional.empty();
    public static boolean connect() {
        try {
            connection = Optional.ofNullable(DriverManager.getConnection(url, user, password));
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public static Session login(String username, String password) {
        try {
            // TODO: What if connection is not ready?
            Connection conn = SqlController.getConnection();
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

    public static Connection getConnection() {
        return connection.orElseThrow();
    }
}
