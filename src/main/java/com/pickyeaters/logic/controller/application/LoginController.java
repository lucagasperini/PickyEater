package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.model.Administrator;
import com.pickyeaters.logic.model.Pickie;
import com.pickyeaters.logic.model.Restaurateur;
import com.pickyeaters.logic.model.User;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.view.bean.UserBean;

public class LoginController extends VirtualController {
    private final UserDAO userDAO = new UserDAO();
    public UserBean auth(LoginBean loginBean) throws ControllerException, BeanException {

        String token = userDAO.login(loginBean.getEmail(), loginBean.getPassword());
        if(token == null) {
            throw new ControllerException("BAD_AUTH", "Login failed");
        }
        User user = userDAO.getUserInfo(loginBean.getEmail());
        return new UserBean(user);
    }

}
