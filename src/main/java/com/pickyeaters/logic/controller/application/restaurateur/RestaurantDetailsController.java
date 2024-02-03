package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.model.Restaurateur;
import com.pickyeaters.logic.view.bean.RestaurateurBean;

public class RestaurantDetailsController extends VirtualController {
    public RestaurantDetailsController(MainController main) {
        super(main);
    }
    public RestaurateurBean get() throws LoginControllerException {
        Restaurateur restaurateur = main.getLogin().toRestaurateur();
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

    public void set(RestaurateurBean restaurateurBean) throws ControllerException {
        Restaurateur restaurateur = new Restaurateur(main.getLogin().toRestaurateur());
        restaurateur.setEmail(restaurateurBean.getEmail());
        restaurateur.setFirstname(restaurateurBean.getFirstname());
        restaurateur.setLastname(restaurateurBean.getLastname());
        restaurateur.setPhone(restaurateurBean.getPhone());
        restaurateur.setSsn(restaurateurBean.getSsn());
        restaurateur.getRestaurant().setName(restaurateurBean.getRestaurantName());
        restaurateur.getRestaurant().setPhone(restaurateurBean.getRestaurantPhone());
        restaurateur.getRestaurant().setAddress(restaurateurBean.getRestaurantAddress());

        UserDAO.getInstance().updateUser(restaurateur);
        main.getLogin().setUser(restaurateur);
    }
}
