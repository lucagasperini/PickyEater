package com.pickyeaters.logic.model;

import java.util.List;

public class EatingPreferences {
    private List<Ingredient> ingredientList;
    private List<ExcludedGroup> groupList;
    private List<Allergy> allergyList;

    public EatingPreferences(List<Ingredient> ingredientList, List<ExcludedGroup> groupList, List<Allergy> allergyList) {
        setIngredientList(ingredientList);
        setGroupList(groupList);
        setAllergyList(allergyList);
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<Allergy> getAllergyList() {
        return allergyList;
    }

    public List<ExcludedGroup> getGroupList() {
        return groupList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setAllergyList(List<Allergy> allergyList) {
        this.allergyList = allergyList;
    }

    public void setGroupList(List<ExcludedGroup> groupList) {
        this.groupList = groupList;
    }
}
