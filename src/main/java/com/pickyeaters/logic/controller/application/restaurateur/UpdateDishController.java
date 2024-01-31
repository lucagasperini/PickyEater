package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.view.bean.DishBean;

public class UpdateDishController extends VirtualController {
    public UpdateDishController(MainController main) {
        super(main);
    }

    public void update(DishBean dishBean) throws ControllerException {
        try {
            String restaurantID = main.getLogin().toRestaurateur().getRestaurant().getID();
            String dishID = DishDAO.getInstance().addDish(restaurantID, dishBean.toDish());
            for(String i : dishBean.getIngredientList())
                DishDAO.getInstance().addDishIngredient(dishID, i);
        } catch (LoginControllerException ex) {
            throw new ControllerException("Current user is not a restaurateur");
        } catch (DAOException ex) {
            throw new ControllerException("Database error");
        }
    }
}
