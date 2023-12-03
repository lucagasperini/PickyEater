package com.pickyeater.lib.model;

import java.util.List;

public class Pickie extends User{
    List<Ingredient> excludeIngredientList;
    public Pickie(int id, String username, String password) {
        super(id, username, password);
    }
    public void addExcludeIngredient(Ingredient ingredient) {
        this.excludeIngredientList.add(ingredient);
    }
}
