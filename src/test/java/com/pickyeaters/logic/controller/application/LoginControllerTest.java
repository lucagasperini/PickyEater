package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.view.bean.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @BeforeEach
    void setUp() throws SettingsControllerException, DatabaseControllerException {
        SettingsController.getInstance().init();
        DatabaseController.getInstance().init();
    }

    @Test
    void auth() throws ControllerException, BeanException {
        LoginController controller = new LoginController();
        UserBean user = controller.auth(new LoginBean("lucar", "luca"));
        assertEquals("lucar", user.getEmail());
    }
}