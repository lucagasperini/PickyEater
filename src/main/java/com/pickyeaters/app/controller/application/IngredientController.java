package com.pickyeaters.app.controller.application;

import com.pickyeaters.app.model.Ingredient;

import java.util.Vector;

public class IngredientController {
    private Vector<Ingredient> ingredientVector = new Vector<>();
    public void add(String name) {
        Ingredient ingredient = new Ingredient(name);
        ingredientVector.add(ingredient);
    }
}
