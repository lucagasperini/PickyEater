package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.model.*;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.bean.EditDishBean;

public class AddDishController extends VirtualController {
    public void add(EditDishBean dishBean, String restaurantID) throws ControllerException {
        try {
            if(dishBean.getIngredientList().isEmpty()) {
                throw new ControllerException("DISH_NO_INGREDIENT","Cannot add dish without ingredients");
            }
            Dish dish = dishBean.toDish();
            DishDAO.getInstance().addDish(restaurantID, dish);
            for(DishIngredientBean i : dishBean.getIngredientList())
                DishDAO.getInstance().addDishIngredient(
                        dishBean.getName(),
                        restaurantID,
                        i.getName(),
                        i.isCooked(),
                        i.isOptional()
                );
        } catch (LoginControllerException ex) {
            throw new ControllerException("Current user is not a restaurateur");
        }
    }
}
