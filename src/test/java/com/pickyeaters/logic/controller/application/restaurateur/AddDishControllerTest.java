package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.bean.EditDishBean;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.view.bean.UserBean;

import static org.junit.jupiter.api.Assertions.*;
class AddDishControllerTest {

    UserBean user;
    @org.junit.jupiter.api.BeforeEach
    void setUp() throws ControllerException, BeanException {
        SettingsController.getInstance().init();
        DatabaseController.getInstance().init();
        LoginController loginController = new LoginController();
        user = loginController.auth(new LoginBean("lucar", "luca"));
        MenuDetailsController menuDetailsController = new MenuDetailsController();
        menuDetailsController.deleteDish("test", user.getRestaurant().getID());
    }

    @org.junit.jupiter.api.Test
    void add() throws BeanException, ControllerException {
        AddDishController controller = new AddDishController();
        EditDishBean dishBean = new EditDishBean("test", "TEST", "DRINK");
        dishBean.getIngredientList().add(new DishIngredientBean("Carne"));
        controller.add(dishBean, user.getRestaurant().getID());
    }
}