package com.pickyeaters.logic.controller.application.pickie;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.*;
import com.pickyeaters.logic.model.Allergy;
import com.pickyeaters.logic.model.EatingPreferences;
import com.pickyeaters.logic.model.ExcludedGroup;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.view.bean.EatingPreferenceBean;
import com.pickyeaters.logic.view.bean.PreferenceIngredientBean;
import com.pickyeaters.logic.view.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class EatingPreferencesController extends VirtualController {
    private final EatingPreferenceDAO eatingPreferenceDAO = new EatingPreferenceDAO();
    private final UserDAO userDAO = new UserDAO();

    public List<String> getAllergyList() throws DAOException {
        AllergyDAO allergyDAO = new AllergyDAO();
        List<Allergy> allergyList = allergyDAO.getAll();
        List<String> out = new ArrayList<>();
        for(Allergy allergy : allergyList) {
            out.add(allergy.getName());
        }
        return out;
    }
    public EatingPreferenceBean getUserPreferences(UserBean userBean) throws DAOException, BeanException {
        return new EatingPreferenceBean(
                eatingPreferenceDAO.get(userDAO.getUserInfo(userBean.getEmail()))
        );
    }

    public void setUserPreferences(EatingPreferenceBean bean, UserBean userBean) throws DAOException, BeanException {
        List<Ingredient> ingredientList = new ArrayList<>();
        List<ExcludedGroup> groupList = new ArrayList<>();
        List<Allergy> allergyList = new ArrayList<>();

        final IngredientDAO ingredientDAO = new IngredientDAO();
        final AllergyDAO allergyDAO = new AllergyDAO();
        final ExcludedGroupDAO excludedGroupDAO = new ExcludedGroupDAO();

        for(String allergy : bean.getAllergyList()) {
            allergyList.add(allergyDAO.get(allergy));
        }

        for(PreferenceIngredientBean ingredient : bean.getIngredientList()) {
            ingredientList.add(ingredientDAO.get(ingredient.getName()));
        }

        if(bean.isCarnivore()) {
            groupList.add(excludedGroupDAO.get("CARNIVORE"));
        }
        if(bean.isHalal()) {
            groupList.add(excludedGroupDAO.get("HALAL"));
        }
        if(bean.isKosher()) {
            groupList.add(excludedGroupDAO.get("KOSHER"));
        }
        if(bean.isPescatarian()) {
            groupList.add(excludedGroupDAO.get("PESCATARIAN"));
        }
        if(bean.isVegan()) {
            groupList.add(excludedGroupDAO.get("VEGAN"));
        }
        if(bean.isVegetarian()) {
            groupList.add(excludedGroupDAO.get("VEGETARIAN"));
        }
        if(bean.isPregnant()) {
            groupList.add(excludedGroupDAO.get("PREGNANT"));
        }

        eatingPreferenceDAO.set(
                new EatingPreferences(ingredientList, groupList, allergyList),
                userDAO.getUserInfo(userBean.getEmail())
        );
    }
}
