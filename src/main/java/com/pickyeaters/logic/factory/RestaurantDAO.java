package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Restaurant;

import java.sql.Types;

public class RestaurantDAO {

    private static final RestaurantDAO instance = new RestaurantDAO();

    private RestaurantDAO() {}

    public static RestaurantDAO getInstance() {
        return instance;
    }

    public Restaurant get(String id) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL restinfo(?, ?, ?, ?)");
            query.setString(id);
            query.registerOutParameter(Types.VARCHAR);
            query.registerOutParameter(Types.VARCHAR);
            query.registerOutParameter(Types.VARCHAR);

            query.execute();

            String name = query.getString();
            String phone = query.getString();
            String address = query.getString();
            query.close();

            return new Restaurant(id, name, phone, address);
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public void update(Restaurant restaurant) throws DAOException {
        try{
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL update_restaurant(?, ?, ?, ?)");
            query.setString(restaurant.getID());
            query.setString(restaurant.getName());
            query.setString(restaurant.getPhone());
            query.setString(restaurant.getAddress());

            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }
}
