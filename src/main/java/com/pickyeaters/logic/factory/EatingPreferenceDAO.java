package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.*;
import com.pickyeaters.logic.view.bean.EatingPreferenceBean;
import com.pickyeaters.logic.view.bean.PreferenceIngredientBean;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EatingPreferenceDAO {
    private final IngredientDAO ingredientDAO = new IngredientDAO();
    private final AllergyDAO allergyDAO = new AllergyDAO();
    private final ExcludedGroupDAO excludedGroupDAO = new ExcludedGroupDAO();
    public EatingPreferences get(User user) throws DAOException, BeanException {
        List<Ingredient> ingredientList;
        List<ExcludedGroup> groupList;
        List<Allergy> allergyList;


        ingredientList = ingredientDAO.getExcludedIngredientList(user.getID());
        groupList = excludedGroupDAO.getExcludedGroupOfUser(user.getID());
        allergyList = allergyDAO.getUserAllergy(user.getID());

        EatingPreferences eatingPreferences = new EatingPreferences(ingredientList, groupList, allergyList);
        return eatingPreferences;
    }
    public void clearUserPreference(User user) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query(
                    "CALL clear_user_preference(?)"
            );
            query.setString(user.getID());
            query.execute();
            query.close();
        } catch (DatabaseControllerException e) {
            throw new DAOException(e);
        }
    }
    public void set(EatingPreferences eatingPreferences, User user) throws DAOException, BeanException {
        try {
            clearUserPreference(user);
            for(Ingredient ingredient : eatingPreferences.getIngredientList()) {
                ingredientDAO.addUserExcludedIngredient(ingredient, user);
            }

            for(Allergy allergy : eatingPreferences.getAllergyList()) {
                allergyDAO.addUserAllergy(allergy, user);
            }

            for(ExcludedGroup group : eatingPreferences.getGroupList()) {
                excludedGroupDAO.addUserExcludedGroup(group, user);
            }
        } catch (DatabaseControllerException e) {
            throw new DAOException(e);
        }

    }
}
