package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.model.bean.IngredientBean;
import com.pickyeaters.app.controller.graphics.IngredientViewController;

import java.util.Scanner;

public class IngredientView {

    private IngredientViewController controller = new IngredientViewController(this);

    public void show(String[] args) {
        controller.request(args);
    }

    public IngredientBean askIngredient() {
        Scanner userInput = new Scanner(System.in);
        IngredientBean ingredientBean = new IngredientBean();
        System.out.print("Ingredient name: ");
        ingredientBean.setName(userInput.nextLine());
        return ingredientBean;
    }
}
