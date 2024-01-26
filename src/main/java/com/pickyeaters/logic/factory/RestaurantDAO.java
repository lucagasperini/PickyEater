package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Restaurant;

import java.sql.Types;

public class RestaurantDAO {

    private static RestaurantDAO instance = new RestaurantDAO();

    private RestaurantDAO() {}

    public static RestaurantDAO getInstance() {
        return instance;
    }

    public Restaurant getRestaurant(String id) throws DAOException {
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

            return new Restaurant(name, phone, address);
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }
}
