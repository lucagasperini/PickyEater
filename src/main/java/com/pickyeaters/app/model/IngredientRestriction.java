package com.pickyeaters.app.model;

public class IngredientRestriction {
    private int id;
    private String name;
    private String description;
    private Ingredient[] ingredientList;
    public IngredientRestriction(int id, String name, String description, Ingredient[] ingredientList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredientList = ingredientList;
    }
}
