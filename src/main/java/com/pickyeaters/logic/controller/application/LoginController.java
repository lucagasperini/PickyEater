package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.model.Session;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.app.view.bean.LoginBean;

public class LoginController {

    private Session session = null;

    public LoginController() {

    }

    public void login(LoginBean loginBean) throws LoginControllerException {
        try {
            session = DatabaseController.getInstance().login(loginBean.getUsername(), loginBean.getPassword());
            if(!session.isValid()) {
                throw new LoginControllerException("Login failed");
            }
        } catch (DatabaseControllerException ex) {
            throw new LoginControllerException("Cannot connect to database");
        }
    }
}
