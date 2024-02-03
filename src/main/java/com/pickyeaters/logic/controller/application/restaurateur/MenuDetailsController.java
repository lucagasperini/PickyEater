package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.ShowDishBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MenuDetailsController extends VirtualController {
    public MenuDetailsController(MainController main) {
        super(main);
    }

    private final AddDishController addDish = new AddDishController(main);
    private final UpdateDishController updateDish = new UpdateDishController(main);
    public AddDishController getAddDish() {
        return addDish;
    }

    public UpdateDishController getUpdateDish() {
        return updateDish;
    }

    public List<ShowDishBean> getMenu() throws ControllerException, BeanException {
        try {
            List<ShowDishBean> out = new ArrayList<>();
            String restaurantID = main.getLogin().toRestaurateur().getRestaurant().getID();
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
        } catch (LoginControllerException ex) {
            throw new ControllerException("Current user is not a restaurateur");
        }
    }

    public void deleteDish(String name) throws ControllerException {
        String restaurantID = main.getLogin().toRestaurateur().getRestaurant().getID();
        DishDAO.getInstance().delete(name, restaurantID);
    }

    public void toggleDish(String name) throws ControllerException {
        String restaurantID = main.getLogin().toRestaurateur().getRestaurant().getID();
        DishDAO.getInstance().toggle(name, restaurantID);
    }
}
