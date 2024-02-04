package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.model.*;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.bean.EditDishBean;

public class AddDishController extends VirtualController {
    private final DishDAO dishDAO = new DishDAO();
    public void add(EditDishBean dishBean, String restaurantID) throws ControllerException {
        try {
            if(dishBean.getIngredientList().isEmpty()) {
                throw new ControllerException("DISH_NO_INGREDIENT","Cannot add dish without ingredients");
            }
            Dish dish = dishBean.toDish();
            dishDAO.addDish(dish, restaurantID);
            try {
                for(DishIngredientBean i : dishBean.getIngredientList()) {
                    dishDAO.addDishIngredient(
                            dishBean.getName(),
                            restaurantID,
                            i.getName(),
                            i.isCooked(),
                            i.isOptional()
                    );
                }
            } catch (DAOException ex) {
                dishDAO.delete(dish.getName(), restaurantID);
                throw new ControllerException("DISH_INVALID_INGREDIENT","Cannot add dish with invalid ingredient");
            }
        } catch (LoginControllerException ex) {
            throw new ControllerException("Current user is not a restaurateur");
        }
    }
}
