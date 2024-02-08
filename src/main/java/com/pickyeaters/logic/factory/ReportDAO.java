package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.model.User;
import com.pickyeaters.logic.utils.QueryProcedure;

public class ReportDAO {
    public void addDishReportMissingIngredient(User user, Dish dish, String description) throws DAOException {
        try {
            QueryProcedure query = DatabaseController.getInstance().queryProcedure(
                    "CALL add_report_dish_missing_ingredient(?, ?, ?)"
            );
            query.setString(user.getID());
            query.setString(dish.getID());
            query.setString(description);

            query.execute();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public void addDishReportUnusedIngredient(User user, Dish dish, String description) throws DAOException {
        try {
            QueryProcedure query = DatabaseController.getInstance().queryProcedure(
                    "CALL add_report_dish_unused_ingredient(?, ?, ?)"
            );
            query.setString(user.getID());
            query.setString(dish.getID());
            query.setString(description);

            query.execute();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

}
