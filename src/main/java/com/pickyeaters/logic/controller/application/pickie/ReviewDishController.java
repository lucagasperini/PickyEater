package com.pickyeaters.logic.controller.application.pickie;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.*;
import com.pickyeaters.logic.model.*;
import com.pickyeaters.logic.view.bean.*;

import java.util.ArrayList;
import java.util.List;

public class ReviewDishController extends VirtualController {
    private final ReviewDAO reviewDAO = new ReviewDAO();
    private final UserDAO userDAO = new UserDAO();
    private final DishDAO dishDAO = new DishDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final RestaurantDAO restaurantDAO = new RestaurantDAO();

    public List<CityBean> getCityList() throws DAOException {
        List<CityBean> out = new ArrayList<>();
        List<City> cityList = cityDAO.getAll();
        for(City city : cityList) {
            out.add(new CityBean(city.getName()));
        }
        return out;
    }

    public List<RestaurantBean> getRestaurantList(CityBean cityBean) throws DAOException {
        List<RestaurantBean> out = new ArrayList<>();
        List<Restaurant> restaurantList = restaurantDAO.getAll(new City(cityBean.getName()));
        for(Restaurant restaurant : restaurantList) {
            out.add(new RestaurantBean(restaurant));
        }
        return out;
    }

    public List<DishBean> getDishList(RestaurantBean restaurantBean) throws DAOException, BeanException {
        List<DishBean> out = new ArrayList<>();
        List<Dish> dishList = dishDAO.getRestaurantDishList(restaurantBean.getID());
        for(Dish dish : dishList) {
            out.add(new DishBean(dish.getID(), dish.getName()));
        }
        return out;
    }


    public void addReview(ReviewBean bean, UserBean user) throws DAOException {
        Pickie pickie = userDAO.getPickie(user.getID());
        Dish dish = dishDAO.get(bean.getDishID());
        Review review = new Review(pickie, dish, bean.getGrade());
        reviewDAO.add(review);
    }
}
