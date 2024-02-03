package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.bean.EditDishBean;

public class UpdateDishController extends VirtualController {
    public UpdateDishController(MainController main) {
        super(main);
    }

    public EditDishBean get(String name) throws ControllerException, BeanException {
        String restaurantID = main.getLogin().toRestaurateur().getRestaurant().getID();
        Dish dish = DishDAO.getInstance().get(name, restaurantID);
        EditDishBean dishBean = new EditDishBean(dish.getName(), dish.getDescription(), dish.getType());
        for(Ingredient i : dish.getIngredientList()) {
            dishBean.getIngredientList().add(
                    new DishIngredientBean(i.getName(), i.isCooked(), i.isOptional())
            );
        }
        return dishBean;
    }

    public void update(EditDishBean dishBean) throws ControllerException {
        if(dishBean.getIngredientList().isEmpty()) {
            throw new ControllerException("DISH_NO_INGREDIENT","Cannot update dish without ingredients");
        }
        DishDAO.getInstance().update(dishBean.toDish());
        String restaurantID = main.getLogin().toRestaurateur().getRestaurant().getID();
        DishDAO.getInstance().unlinkIngredient(dishBean.getName(), restaurantID);
        for(DishIngredientBean i : dishBean.getIngredientList()) {
            DishDAO.getInstance().addDishIngredient(
                    dishBean.getName(),
                    restaurantID,
                    i.getName(),
                    i.isCooked(),
                    i.isOptional()
            );
        }
    }
}
