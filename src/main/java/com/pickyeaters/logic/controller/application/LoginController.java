package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.model.User;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.view.bean.UserBean;

public class LoginController {

    private User user = null;

    public LoginController() {

    }

    public boolean isAuth() {
        return user != null;
    }

    public UserBean getUser() {
        return new UserBean(user);
    }

    public void auth(LoginBean loginBean) throws LoginControllerException {
        try {
            if(!DatabaseController.getInstance().login(loginBean.getEmail(), loginBean.getPassword())) {
                throw new LoginControllerException("Login failed");
            }
            user = UserDAO.getInstance().getUserInfo(loginBean.getEmail());
        } catch (DatabaseControllerException ex) {
            throw new LoginControllerException("Cannot connect to database");
        }
    }
}
