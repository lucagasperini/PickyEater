package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.model.Administrator;
import com.pickyeaters.logic.model.Pickie;
import com.pickyeaters.logic.model.Restaurateur;
import com.pickyeaters.logic.model.User;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.view.bean.RestaurateurBean;
import com.pickyeaters.logic.view.bean.UserBean;

public class LoginController extends VirtualController {

    public enum UserType {
        PICKIE,
        RESTAURATEUR,
        ADMIN
    }

    private User user = null;

    public LoginController(MainController main) {
        super(main);
    }

    public boolean isAuth() {
        return user != null;
    }

    public UserBean getUser() {
        return new UserBean(user);
    }

    public UserType getUserType() throws LoginControllerException {
        if(user instanceof Pickie) {
            return UserType.PICKIE;
        } else if (user instanceof Restaurateur) {
            return UserType.RESTAURATEUR;
        } else if (user instanceof Administrator) {
            return UserType.ADMIN;
        } else {
            throw new LoginControllerException("Cannot identify user type");
        }
    }

    public void setRestaurateur(RestaurateurBean restaurateurBean) throws LoginControllerException {
        Restaurateur restaurateur = toRestaurateur();

    }

    public Restaurateur toRestaurateur() throws LoginControllerException {
        if(getUserType() == UserType.RESTAURATEUR) {
            return (Restaurateur) user;
        }
        throw new LoginControllerException("Cannot cast to restaurateur");
    }

    public void auth(LoginBean loginBean) throws LoginControllerException {
        try {
            if(!DatabaseController.getInstance().login(loginBean.getEmail(), loginBean.getPassword())) {
                throw new LoginControllerException("Login failed");
            }
            user = UserDAO.getInstance().getUserInfo(loginBean.getEmail());
            switch (getUserType()) {
                case PICKIE -> main.initPickie();
                case RESTAURATEUR -> main.initRestaurateur();
                case ADMIN -> main.initAdministrator();
            }
        } catch (DatabaseControllerException ex) {
            throw new LoginControllerException("Cannot connect to database");
        }
    }

    public void logout() {
        user = null;
    }
}
