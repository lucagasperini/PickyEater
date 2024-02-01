package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.view.bean.DishBean;

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

    public List<DishBean> getMenu() throws ControllerException {
        try {
            LinkedList<DishBean> out = new LinkedList<>();
            String restaurantID = main.getLogin().toRestaurateur().getRestaurant().getID();
            List<Dish> dishList = DishDAO.getInstance().getRestaurantDishList(restaurantID);
            for(Dish i : dishList) {
                List<Ingredient> ingredientList = IngredientDAO.getInstance().getIngredientListOfDish(i.getID());
                i.addIngredientList(ingredientList);
                out.add(new DishBean(i));
            }
            return out;
        } catch (LoginControllerException ex) {
            throw new ControllerException("Current user is not a restaurateur");
        }
    }

    public void deleteDish(String dishID) throws ControllerException {
        DishDAO.getInstance().delete(dishID);
    }

    public void toggleDish(String dishID) throws ControllerException {
        DishDAO.getInstance().toggle(dishID);
    }
}
