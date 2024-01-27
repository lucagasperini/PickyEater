package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.*;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;

public class UserDAO {
    private static UserDAO instance = new UserDAO();
    private UserDAO() {}

    public static UserDAO getInstance() {
        return instance;
    }

    public User getUserInfo(String email) throws DAOException {
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
                return getUserInfoPickie(id, email, firstname, lastname);
            }
            if(type.equals("ADMIN")) {
                return new Administrator(id, email, firstname, lastname);
            }
            if(type.equals("REST")) {
                return getUserInfoRestaurateur(id, email, firstname, lastname);
            }

            throw new DAOException("Cannot identify this kind of user: " + type);
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    private Pickie getUserInfoPickie(String id, String email, String firstname, String lastname) throws DatabaseControllerException {
        DatabaseController.Query query = DatabaseController.getInstance().query("CALL userinfo_pickie(?, ?)");
        query.setString(email);
        query.registerOutParameter(Types.VARCHAR);
        query.execute();
        String username = query.getString();
        return new Pickie(id, email, username, firstname, lastname);
    }
    private Restaurateur getUserInfoRestaurateur(String id, String email, String firstname, String lastname) throws DatabaseControllerException {
        DatabaseController.Query query = DatabaseController.getInstance().query("CALL userinfo_rest(?, ?, ?)");
        query.setString(email);
        query.registerOutParameter(Types.VARCHAR);
        query.registerOutParameter(Types.VARCHAR);
        query.execute();
        String ssn = query.getString();
        String restID = query.getString();
        query.close();

        Restaurant rest = RestaurantDAO.getInstance().get(restID);

        return new Restaurateur(id, email, firstname, lastname, ssn, rest);
    }
}
