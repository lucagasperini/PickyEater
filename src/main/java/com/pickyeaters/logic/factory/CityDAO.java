package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.City;
import com.pickyeaters.logic.utils.QueryResultSet;

import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    public City get(String name) {
        return new City(name);
    }
    public List<City> getAll() throws DAOException {
        try {
            List<City> out = new ArrayList<>();
            QueryResultSet query = DatabaseController.getInstance().queryResultSet("SELECT name FROM all_city");

            query.execute();
            while(query.next()) {
                out.add(new City(query.getString()));
            }
            query.close();

            return out;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

}
