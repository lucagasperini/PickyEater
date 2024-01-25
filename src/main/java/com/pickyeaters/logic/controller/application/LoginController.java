package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.view.bean.LoginBean;

public class LoginController {

    public LoginController() {

    }

    public void login(LoginBean loginBean) throws LoginControllerException {
        try {
            if(!DatabaseController.getInstance().login(loginBean.getUsername(), loginBean.getPassword())) {
                throw new LoginControllerException("Login failed");
            }
        } catch (DatabaseControllerException ex) {
            throw new LoginControllerException("Cannot connect to database");
        }
    }
}
