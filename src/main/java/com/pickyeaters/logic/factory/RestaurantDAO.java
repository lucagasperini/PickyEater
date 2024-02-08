package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.City;
import com.pickyeaters.logic.model.Restaurant;
import com.pickyeaters.logic.model.User;
import com.pickyeaters.logic.utils.QueryProcedure;
import com.pickyeaters.logic.utils.QueryResultSet;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    public List<Restaurant> findRestaurant(User user, City city) throws DAOException {
        try {
            List<Restaurant> out = new ArrayList<>();
            QueryResultSet query = DatabaseController.getInstance().queryResultSet(
                    "SELECT restaurant_id, restaurant_name, restaurant_phone, restaurant_address FROM find_restaurant WHERE user_id = ? AND restaurant_city = ?"
            );
            query.setString(user.getID());
            query.setString(city.getName());

            query.execute();

            while (query.next()) {
                out.add(new Restaurant(
                        query.getString(),
                        query.getString(),
                        query.getString(),
                        query.getString(),
                        city
                ));
            }

            query.close();

            return out;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }
    public Restaurant get(String id) throws DAOException {
        try {
            QueryProcedure query = DatabaseController.getInstance().queryProcedure("CALL restinfo(?, ?, ?, ?, ?)");
            query.setString(id);
            query.registerOutString();
            query.registerOutString();
            query.registerOutString();
            query.registerOutString();

            query.execute();

            String name = query.getString();
            String phone = query.getString();
            String address = query.getString();
            String city = query.getString();
            query.close();

            return new Restaurant(id, name, phone, address, new City(city));
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public List<Restaurant> getAll(City city) throws DAOException {
        try {
            List<Restaurant> out = new ArrayList<>();
            QueryResultSet query = DatabaseController.getInstance().queryResultSet(
                    "SELECT restaurant_id, restaurant_name, restaurant_phone, restaurant_address FROM all_restaurant WHERE restaurant_city = ?"
            );

            query.setString(city.getName());

            query.execute();

            while (query.next()) {
                out.add(new Restaurant(
                        query.getString(),
                        query.getString(),
                        query.getString(),
                        query.getString(),
                        city
                ));
            }

            query.close();

            return out;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public void update(Restaurant restaurant) throws DAOException {
        try{
            QueryProcedure query = DatabaseController.getInstance().queryProcedure("CALL update_restaurant(?, ?, ?, ?, ?)");
            query.setString(restaurant.getID());
            query.setString(restaurant.getName());
            query.setString(restaurant.getPhone());
            query.setString(restaurant.getAddress());
            query.setString(restaurant.getCity().getName());

            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }
}
