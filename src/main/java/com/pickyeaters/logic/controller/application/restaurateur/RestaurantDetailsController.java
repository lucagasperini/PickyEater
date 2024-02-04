package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.model.Restaurateur;
import com.pickyeaters.logic.view.bean.RestaurateurBean;

public class RestaurantDetailsController extends VirtualController {
    private final UserDAO userDAO = new UserDAO();
    public RestaurateurBean get(String email) throws DAOException {
        // TODO: Handle this cast safely
        Restaurateur restaurateur = (Restaurateur) userDAO.getUserInfo(email);
        return new RestaurateurBean(
                restaurateur.getEmail(),
                restaurateur.getFirstname(),
                restaurateur.getLastname(),
                restaurateur.getPhone(),
                restaurateur.getSsn(),
                restaurateur.getRestaurant().getName(),
                restaurateur.getRestaurant().getPhone(),
                restaurateur.getRestaurant().getAddress()
        );
    }

    public void set(RestaurateurBean restaurateurBean, String restaurateurID, String restaurantID) throws ControllerException {
        Restaurateur restaurateur = restaurateurBean.toModel();
        restaurateur.setID(restaurateurID);
        restaurateur.getRestaurant().setID(restaurantID);
        userDAO.updateUser(restaurateur);
    }
}
