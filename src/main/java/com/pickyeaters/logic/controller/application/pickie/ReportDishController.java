package com.pickyeaters.logic.controller.application.pickie;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.DishDAO;
import com.pickyeaters.logic.factory.ReportDAO;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.UserBean;

public class ReportDishController extends VirtualController {
    private final ReportDAO reportDAO = new ReportDAO();
    private final UserDAO userDAO = new UserDAO();
    private final DishDAO dishDAO = new DishDAO();

    public void addReportMissingIngredient(UserBean user, DishBean dish, String description) throws DAOException {
        reportDAO.addDishReportMissingIngredient(
                userDAO.getUser(user.getID()),
                dishDAO.get(dish.getID()),
                description
        );
    }
    public void addReportUnusedIngredient(UserBean user, DishBean dish, String description) throws DAOException {
        reportDAO.addDishReportUnusedIngredient(
                userDAO.getUser(user.getID()),
                dishDAO.get(dish.getID()),
                description
        );
    }
}
