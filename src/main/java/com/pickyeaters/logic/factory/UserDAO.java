package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.*;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class UserDAO {
    private static UserDAO instance = new UserDAO();
    private UserDAO() {}

    public static UserDAO getInstance() {
        return instance;
    }

    public String login(String username, String password) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL login(?,?,?)");

            query.setString(username);
            query.setString(password);
            query.registerOutParameter(Types.VARCHAR);

            query.execute();

            String token = query.getString();
            query.close();

            return token;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }

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
            query.close();
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

    private Pickie getUserInfoPickie(
            String id,
            String email,
            String firstname,
            String lastname) throws DatabaseControllerException {
        DatabaseController.Query query = DatabaseController.getInstance().query("CALL userinfo_pickie(?, ?)");
        query.setString(email);
        query.registerOutParameter(Types.VARCHAR);
        query.execute();
        String username = query.getString();
        query.close();
        return new Pickie(id, email, username, firstname, lastname);
    }
    private Restaurateur getUserInfoRestaurateur(
            String id,
            String email,
            String firstname,
            String lastname) throws DatabaseControllerException {
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

    public void updateUser(User user) throws DAOException {
        try {
            if (user instanceof Pickie) {
                updateUserPickie((Pickie) user);
            } else if (user instanceof Administrator) {
                updateUserAdministrator((Administrator) user);
            } else if (user instanceof Restaurateur) {
                updateUserRestaurateur((Restaurateur) user);
            } else {
                throw new DAOException("Cannot identify this kind of user");
            }
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    private void updateUserPickie(Pickie pickie) throws DatabaseControllerException {
        DatabaseController.Query query =
                DatabaseController.getInstance().query("CALL update_pickie(?, ?, ?, ?, ?)");
        query.setString(pickie.getID());
        query.setString(pickie.getEmail());
        query.setString(pickie.getFirstname());
        query.setString(pickie.getLastname());
        query.setString(pickie.getUsername());

        query.execute();
        query.close();
    }

    private void updateUserAdministrator(Administrator administrator) throws DatabaseControllerException {
        DatabaseController.Query query =
                DatabaseController.getInstance().query("CALL update_administrator(?, ?, ?, ?)");
        query.setString(administrator.getID());
        query.setString(administrator.getEmail());
        query.setString(administrator.getFirstname());
        query.setString(administrator.getLastname());

        query.execute();
        query.close();
    }

    private void updateUserRestaurateur(Restaurateur restaurateur) throws DatabaseControllerException {
        DatabaseController.Query query =
                DatabaseController.getInstance().query("CALL update_restaurateur(?, ?, ?, ?, ?)");
        query.setString(restaurateur.getID());
        query.setString(restaurateur.getEmail());
        query.setString(restaurateur.getFirstname());
        query.setString(restaurateur.getLastname());
        query.setString(restaurateur.getSsn());

        query.execute();
        query.close();

        if(restaurateur.getRestaurant() != null) {
            RestaurantDAO.getInstance().update(restaurateur.getRestaurant());
        }
    }
}
