package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.DishIngredientBean;

public class UpdateDishController extends VirtualController {
    public UpdateDishController(MainController main) {
        super(main);
    }

    public DishBean get(String dishID) throws ControllerException {
        try {
            return new DishBean(DishDAO.getInstance().get(dishID));
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(DishBean dishBean) throws ControllerException {
        if(!dishBean.hasIngredients()) {
            throw new ControllerException("DISH_NO_INGREDIENT","Cannot update dish without ingredients");
        }
        DishDAO.getInstance().update(dishBean.toDish());
        DishDAO.getInstance().unlinkIngredient(dishBean.getID());
        for(DishIngredientBean i : dishBean.getIngredientList())
            DishDAO.getInstance().addDishIngredient(dishBean.getID(), i);
    }
}
