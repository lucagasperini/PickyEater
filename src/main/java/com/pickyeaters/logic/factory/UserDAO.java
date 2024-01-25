package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Administrator;
import com.pickyeaters.logic.model.Pickie;
import com.pickyeaters.logic.model.Restaurateur;
import com.pickyeaters.logic.model.User;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;

public class UserDAO {
    private static UserDAO instance = new UserDAO();

    public static UserDAO getInstance() {
        return instance;
    }

    public User getUserInfo(String email) {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL userinfo(?, ?, ?, ?, ?)");
            query.setString(email);
            query.registerOutParameter(Types.VARCHAR);
            query.registerOutParameter(Types.VARCHAR);
            query.registerOutParameter(Types.VARCHAR);
            query.registerOutParameter(Types.VARCHAR);

            query.execute();

            String id = query.getString();
            String type = query.getString();
            String firstname = query.getString();
            String lastname = query.getString();
            if(type.equals("PICKIE")) {
                return new Pickie(id, email, "", firstname, lastname);
            }
            if(type.equals("ADMIN")) {
                return new Administrator(id, email, firstname, lastname);
            }
            if(type.equals("REST")) {
                //TODO:
                //return new Restaurateur(id, username, firstname, lastname);
            }

            return null;
        } catch (DatabaseControllerException ex) {
            throw new RuntimeException(ex);
        }
    }
}
