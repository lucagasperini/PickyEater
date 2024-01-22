package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.model.bean.IngredientBean;
import com.pickyeaters.logic.controller.graphics.IngredientViewController;

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
