package com.pickyeaters.logic.model;

import java.util.List;

public class Pickie extends User{
    private EatingPreferences eatingPreferences;
    String username;
    List<Ingredient> dislikedIngredients; //TODO: sostituire con un generico esigenze alimentari
    public Pickie(String id, String email, String username, String firstname, String lastname) {
        super(id, email, firstname, lastname);
        this.username = username;
    }

    public Pickie(Pickie pickie) {
        super(pickie);
        this.username = pickie.username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addExcludeIngredient(Ingredient ingredient) {
        this.dislikedIngredients.add(ingredient);
    }
}
