package com.pickyeater.cli.controller;

import com.pickyeater.lib.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class UserDAO {
    private User user;

    public UserDAO() {

    }

    public static User exists(String username, String password) {
        try {
            Connection conn = SqlController.getConnection();
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT id, username, password FROM \"User\" WHERE user = ? AND password = ?"
            );
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            /*
            if(rs.next()) {

            }
            */
            //TODO: Implement this?
        } catch (NoSuchElementException ex) {
            // TODO: ERROR
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
