package com.pickyeaters.logic.controller.application.pickie;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.CityDAO;
import com.pickyeaters.logic.factory.RestaurantDAO;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.model.City;
import com.pickyeaters.logic.model.Restaurant;
import com.pickyeaters.logic.view.bean.CityBean;
import com.pickyeaters.logic.view.bean.RestaurantBean;
import com.pickyeaters.logic.view.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class FindRestaurantController extends VirtualController {
    private final CityDAO cityDAO = new CityDAO();
    private final RestaurantDAO restaurantDAO = new RestaurantDAO();
    private final UserDAO userDAO = new UserDAO();
    public List<CityBean> getCityList() throws DAOException {
        List<CityBean> out = new ArrayList<>();
        List<City> cityList = cityDAO.getAll();
        for(City city : cityList) {
            out.add(new CityBean(city.getName()));
        }
        return out;
    }

    public List<RestaurantBean> findRestaurant(UserBean user, CityBean cityBean) throws DAOException {
        List<RestaurantBean> out = new ArrayList<>();
        List<Restaurant> restaurantList = restaurantDAO.findRestaurant(
                userDAO.getUser(user.getID()),
                cityDAO.get(cityBean.getName())
        );
        for(Restaurant restaurant : restaurantList) {
            out.add(new RestaurantBean(restaurant));
        }
        return out;
    }
}
