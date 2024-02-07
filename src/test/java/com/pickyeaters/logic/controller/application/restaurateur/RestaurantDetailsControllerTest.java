package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.CityBean;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.view.bean.RestaurateurBean;
import com.pickyeaters.logic.view.bean.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantDetailsControllerTest {
    UserBean user;
    @BeforeEach
    void setUp() throws ControllerException, BeanException {
        SettingsController.getInstance().init();
        DatabaseController.getInstance().init();

        LoginController loginController = new LoginController();
        user = loginController.auth(new LoginBean("lucar", "luca"));
    }

    @Test
    void set() throws ControllerException {
        RestaurantDetailsController restaurantDetailsController = new RestaurantDetailsController();
        RestaurateurBean beanSet = new RestaurateurBean(
                "lucar",
                "Luke",
                "Test",
                "012345",
                "98765",
                "My test",
                "11111",
                "0",
                new CityBean("Roma")
                );
        restaurantDetailsController.set(beanSet, user);

        RestaurateurBean beanGet = restaurantDetailsController.get(user);
        assertEquals("Luke", beanGet.getFirstname());
        assertEquals("Test", beanGet.getLastname());
        assertEquals("012345", beanGet.getPhone());
        assertEquals("98765", beanGet.getSsn());
        assertEquals("My test", beanGet.getRestaurantName());
        assertEquals("11111", beanGet.getRestaurantPhone());
        assertEquals("0", beanGet.getRestaurantAddress());
        assertEquals("Roma", beanGet.getRestaurantCity().getName());
    }
}