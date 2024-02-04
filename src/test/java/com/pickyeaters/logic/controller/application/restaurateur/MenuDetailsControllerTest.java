package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuDetailsControllerTest {
    UserBean user;
    AddDishController addDishController;
    @BeforeEach
    void setUp() throws ControllerException, BeanException {
        SettingsController.getInstance().init();
        DatabaseController.getInstance().init();

        LoginController loginController = new LoginController();
        user = loginController.auth(new LoginBean("lucar", "luca"));
        addDishController = new AddDishController();
    }

    @Test
    void getMenu() throws ControllerException, BeanException {

        EditDishBean dish1 = new EditDishBean("First", "", "DRINK");
        dish1.getIngredientList().add(new DishIngredientBean("Pollo"));

        EditDishBean dish2 = new EditDishBean("Second", "", "DRINK");
        dish2.getIngredientList().add(new DishIngredientBean("Pollo"));

        EditDishBean dish3 = new EditDishBean("Third", "", "DRINK");
        dish3.getIngredientList().add(new DishIngredientBean("Pollo"));

        addDishController.add(dish1, user.getRestaurant().getID());
        addDishController.add(dish2, user.getRestaurant().getID());
        addDishController.add(dish3, user.getRestaurant().getID());

        MenuDetailsController menuDetailsController = new MenuDetailsController();
        List<ShowDishBean> menu = menuDetailsController.getMenu(user.getRestaurant().getID());
        List<String> names = new ArrayList<>();
        names.add("First");
        names.add("Second");
        names.add("Third");

        int counter = 0;
        for(ShowDishBean i : menu) {
            if(names.contains(i.getName())) {
                counter++;
            }
        }
        assertEquals(3, counter);
    }

    @Test
    void deleteDish() throws ControllerException, BeanException {
        EditDishBean dishRemove = new EditDishBean("To remove", "", "DRINK");
        dishRemove.getIngredientList().add(new DishIngredientBean("Pollo"));

        addDishController.add(dishRemove, user.getRestaurant().getID());

        MenuDetailsController menuDetailsController = new MenuDetailsController();
        menuDetailsController.deleteDish("To remove", user.getRestaurant().getID());
    }

    @Test
    void toggleDish() throws ControllerException, BeanException {
        EditDishBean dishToggle = new EditDishBean("To toggle", "", "DRINK");
        dishToggle.getIngredientList().add(new DishIngredientBean("Pollo"));

        addDishController.add(dishToggle, user.getRestaurant().getID());

        MenuDetailsController menuDetailsController = new MenuDetailsController();
        menuDetailsController.toggleDish("To toggle", user.getRestaurant().getID());

        List<ShowDishBean> menu = menuDetailsController.getMenu(user.getRestaurant().getID());
        for(ShowDishBean i : menu) {
            if(i.getName().equals("To toggle")) {
                assertFalse(i.isActive());
            }
        }
    }
}