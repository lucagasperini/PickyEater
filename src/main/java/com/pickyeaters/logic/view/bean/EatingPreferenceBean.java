package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.model.Allergy;
import com.pickyeaters.logic.model.EatingPreferences;
import com.pickyeaters.logic.model.ExcludedGroup;
import com.pickyeaters.logic.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class EatingPreferenceBean {
    private boolean halal;
    private boolean kosher;
    private boolean pregnant;
    private boolean vegan;
    private boolean vegetarian;
    private boolean carnivore;
    private boolean pescatarian;
    private List<String> allergyList;
    private List<PreferenceIngredientBean> ingredientList;

    public EatingPreferenceBean(EatingPreferences eatingPreferences) throws BeanException {
        allergyList = new ArrayList<>();
        ingredientList = new ArrayList<>();
        for(Allergy allergy : eatingPreferences.getAllergyList()) {
            allergyList.add(allergy.getName());
        }

        for(Ingredient ingredient : eatingPreferences.getIngredientList()) {
            ingredientList.add(new PreferenceIngredientBean(
                    ingredient.getName(),
                    ingredient.isCooked()
            ));
        }

        for(ExcludedGroup group : eatingPreferences.getGroupList()) {
            activeGroup(group.getName());
        }
    }

    private void activeGroup(String groupName) throws BeanException {
        switch (groupName.toUpperCase()) {
            case "HALAL" -> halal = true;
            case "KOSHER" -> kosher = true;
            case "PREGNANT" -> pregnant = true;
            case "VEGAN" -> vegan = true;
            case "VEGETARIAN" -> vegetarian = true;
            case "CARNIVORE" -> carnivore = true;
            case "PESCATARIAN" -> pescatarian = true;
            default -> throw new BeanException("Invalid group name");
        }
    }

    public List<PreferenceIngredientBean> getIngredientList() {
        return ingredientList;
    }

    public List<String> getAllergyList() {
        return allergyList;
    }

    public boolean isCarnivore() {
        return carnivore;
    }

    public boolean isHalal() {
        return halal;
    }

    public boolean isKosher() {
        return kosher;
    }

    public boolean isPescatarian() {
        return pescatarian;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setIngredientList(List<PreferenceIngredientBean> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setAllergyList(List<String> allergyList) {
        this.allergyList = allergyList;
    }

    public void setCarnivore(boolean carnivore) {
        this.carnivore = carnivore;
    }

    public void setHalal(boolean halal) {
        this.halal = halal;
    }

    public void setKosher(boolean kosher) {
        this.kosher = kosher;
    }

    public void setPescatarian(boolean pescatarian) {
        this.pescatarian = pescatarian;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}
