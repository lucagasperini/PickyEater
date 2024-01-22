package com.pickyeaters.logic.model;

import java.util.List;

public class Pickie extends User{
    private EatingPreferences eatingPreferences;
    List<Ingredient> dislikedIngredients; //TODO: sostituire con un generico esigenze alimentari
    public Pickie(String username) {
        super(username);
    }
    public Pickie(String username, String firstname, String lastname) {
        super(username, firstname, lastname);
    }
    public void addExcludeIngredient(Ingredient ingredient) {
        this.dislikedIngredients.add(ingredient);
    }
}
