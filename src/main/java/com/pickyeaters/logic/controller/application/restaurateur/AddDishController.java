package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.DishIngredientBean;

public class AddDishController extends VirtualController {
    public AddDishController(MainController main) {
        super(main);
    }

    public void add(DishBean dishBean) throws ControllerException {
        try {
            if(!dishBean.hasIngredients()) {
                throw new ControllerException("DISH_NO_INGREDIENT","Cannot add dish without ingredients");
            }
            String restaurantID = main.getLogin().toRestaurateur().getRestaurant().getID();
            String dishID = DishDAO.getInstance().addDish(restaurantID, dishBean.toDish());
            for(DishIngredientBean i : dishBean.getIngredientList())
                DishDAO.getInstance().addDishIngredient(dishID, i);
        } catch (LoginControllerException ex) {
            throw new ControllerException("Current user is not a restaurateur");
        }
    }
}
