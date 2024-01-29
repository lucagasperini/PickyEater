package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.model.Ingredient;

import java.util.ArrayList;

public class MenuDetailsController extends VirtualController {
    public MenuDetailsController(MainController main) {
        super(main);
    }

    public ArrayList<String> getIngredients() throws DAOException {
        return IngredientDAO.getInstance().getAll().getPaths();
    }
}
