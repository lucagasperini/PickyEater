package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.bean.EditDishBean;
import com.pickyeaters.logic.view.bean.LoginBean;
import com.pickyeaters.logic.view.bean.UserBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateDishControllerTest {
    UserBean user;
    @org.junit.jupiter.api.BeforeEach
    void setUp() throws ControllerException, BeanException {
        SettingsController.getInstance().init();
        DatabaseController.getInstance().init();

        LoginController loginController = new LoginController();
        user = loginController.auth(new LoginBean("lucar", "luca"));

        AddDishController addDishController = new AddDishController();
        EditDishBean dishBean = new EditDishBean("UpdateDishTest2", "TEST", "DRINK");
        dishBean.getIngredientList().add(new DishIngredientBean("Carne"));
        addDishController.add(dishBean, user.getRestaurant().getID());
    }
    @Test
    void test() throws BeanException, ControllerException {
        UpdateDishController controller = new UpdateDishController();
        EditDishBean dishBean = new EditDishBean("othername", "OTHER DESCRIPTION", "APPETIZER");
        dishBean.getIngredientList().add(new DishIngredientBean("Pane", true, true));

        controller.update(dishBean, "UpdateDishTest2", user.getRestaurant().getID());

        EditDishBean fetch = controller.get(dishBean.getName(), user.getRestaurant().getID());
        assertEquals("othername", fetch.getName());
        assertEquals("OTHER DESCRIPTION", fetch.getDescription());
        assertEquals("APPETIZER", fetch.getCategory());
        DishIngredientBean ingredientBean = fetch.getIngredientList().get(0);
        assertEquals("Pane", ingredientBean.getName());
        assertTrue(ingredientBean.isCooked());
        assertTrue(ingredientBean.isOptional());
    }
}