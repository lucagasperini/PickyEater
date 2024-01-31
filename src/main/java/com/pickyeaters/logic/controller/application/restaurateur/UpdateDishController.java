package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.view.bean.DishBean;

public class UpdateDishController extends VirtualController {
    public UpdateDishController(MainController main) {
        super(main);
    }

    public DishBean get(String dishID) throws ControllerException {
        try {
            return new DishBean(DishDAO.getInstance().get(dishID));
        } catch (DAOException ex) {
            throw new ControllerException("Database error: " + ex.getMessage());
        }
    }

    public void update(DishBean dishBean) throws ControllerException {
        try {
            DishDAO.getInstance().update(dishBean.toDish());
            DishDAO.getInstance().unlinkIngredient(dishBean.getID());
            for(String i : dishBean.getIngredientList())
                DishDAO.getInstance().addDishIngredient(dishBean.getID(), i);
        } catch (DAOException ex) {
            throw new ControllerException("Database error: " + ex.getMessage());
        }
    }
}
