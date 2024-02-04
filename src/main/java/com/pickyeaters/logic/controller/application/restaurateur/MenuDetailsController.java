package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.view.bean.ShowDishBean;

import java.util.ArrayList;
import java.util.List;

public class MenuDetailsController extends VirtualController {
    public List<ShowDishBean> getMenu(String restaurantID) throws ControllerException, BeanException {
        List<ShowDishBean> out = new ArrayList<>();
        List<Dish> dishList = DishDAO.getInstance().getRestaurantDishList(restaurantID);
        for(Dish i : dishList) {
            List<Ingredient> ingredientList = IngredientDAO.getInstance().getIngredientListOfDish(i.getID());
            ShowDishBean bean = new ShowDishBean(
                    i.getName(),
                    i.getDescription(),
                    i.getType(),
                    i.isActive()
            );
            for(Ingredient ingredient : ingredientList) {
                bean.getIngredientList().add(ingredient.getName());
            }
            out.add(bean);
        }
        return out;
    }

    public void deleteDish(String name, String restaurantID) throws ControllerException {
        DishDAO.getInstance().delete(name, restaurantID);
    }

    public void toggleDish(String name, String restaurantID) throws ControllerException {
        DishDAO.getInstance().toggle(name, restaurantID);
    }
}
