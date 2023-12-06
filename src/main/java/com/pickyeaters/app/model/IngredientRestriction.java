package com.pickyeaters.app.model;

public class IngredientRestriction {
    private String name;
    private String description;
    private Ingredient[] ingredientList;
    public IngredientRestriction(String name, String description, Ingredient[] ingredientList) {
        this.name = name;
        this.description = description;
        this.ingredientList = ingredientList;
    }
}
