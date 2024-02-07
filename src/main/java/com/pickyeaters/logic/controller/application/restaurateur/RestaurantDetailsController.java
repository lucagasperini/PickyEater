package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.model.Restaurateur;
import com.pickyeaters.logic.view.bean.RestaurateurBean;
import com.pickyeaters.logic.view.bean.UserBean;

public class RestaurantDetailsController extends VirtualController {
    private final UserDAO userDAO = new UserDAO();
    public RestaurateurBean get(UserBean user) throws DAOException {
        // TODO: Handle this cast safely
        Restaurateur restaurateur = userDAO.getRestaurateur(user.getID());
        return new RestaurateurBean(
                restaurateur.getEmail(),
                restaurateur.getFirstname(),
                restaurateur.getLastname(),
                restaurateur.getPhone(),
                restaurateur.getSsn(),
                restaurateur.getRestaurant().getName(),
                restaurateur.getRestaurant().getPhone(),
                restaurateur.getRestaurant().getAddress(),
                restaurateur.getRestaurant().getCity()
        );
    }

    public void set(RestaurateurBean restaurateurBean, UserBean user) throws ControllerException {
        Restaurateur restaurateur = userDAO.getRestaurateur(user.getID());
        restaurateur.setEmail(restaurateurBean.getEmail());
        restaurateur.setFirstname(restaurateurBean.getFirstname());
        restaurateur.setLastname(restaurateurBean.getLastname());
        restaurateur.setPhone(restaurateurBean.getPhone());
        restaurateur.setSsn(restaurateurBean.getSsn());
        restaurateur.getRestaurant().setName(restaurateurBean.getRestaurantName());
        restaurateur.getRestaurant().setAddress(restaurateurBean.getRestaurantAddress());
        restaurateur.getRestaurant().setPhone(restaurateurBean.getRestaurantPhone());
        restaurateur.getRestaurant().setCity(restaurateurBean.getRestaurantCity());
        userDAO.updateUser(restaurateur);
    }
}
