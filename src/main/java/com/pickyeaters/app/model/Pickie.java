package com.pickyeaters.app.model;

import java.util.List;

public class Pickie extends User{
    List<Ingredient> excludeIngredientList;
    public Pickie(String username) {
        super(username);
    }
    public Pickie(String username, String firstname, String lastname) {
        super(username, firstname, lastname);
    }
    public void addExcludeIngredient(Ingredient ingredient) {
        this.excludeIngredientList.add(ingredient);
    }
}
