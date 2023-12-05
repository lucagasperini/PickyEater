package com.pickyeaters.app.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    public static Connection getConnection() {
        return connection.orElseThrow();
    }
}
