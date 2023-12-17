package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.view.controller.IngredientViewController;

public class IngredientView implements ViewCLI {

    private IngredientViewController controller = new IngredientViewController();

    @Override
    public void show(String[] args) {
        controller.request(args);
    }
}
