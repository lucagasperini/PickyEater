package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.model.Restaurateur;
import com.pickyeaters.logic.view.bean.RestaurateurBean;

public class RestaurantDetailsController extends VirtualController {
    public RestaurantDetailsController(MainController main) {
        super(main);
    }
    public RestaurateurBean get() throws LoginControllerException {
        Restaurateur restaurateur = main.getLoginController().toRestaurateur();
        return new RestaurateurBean(
                restaurateur.getEmail(),
                restaurateur.getFirstname(),
                restaurateur.getLastname(),
                restaurateur.getSsn(),
                restaurateur.getRestaurant().getName(),
                restaurateur.getRestaurant().getPhone(),
                restaurateur.getRestaurant().getAddress()
        );
    }
}
