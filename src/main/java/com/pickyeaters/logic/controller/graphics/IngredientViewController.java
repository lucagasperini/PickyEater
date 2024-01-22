package com.pickyeaters.logic.controller.graphics;

import com.pickyeaters.logic.controller.application.IngredientController;
import com.pickyeaters.logic.model.bean.IngredientBean;
import com.pickyeaters.logic.view.cli.IngredientView;

public class IngredientViewController {
    private IngredientController controller = new IngredientController();
    private IngredientView view;
    public IngredientViewController(IngredientView view) {
        this.view = view;
    }
    public void request(String[] args) {
        switch(args[1].toLowerCase()) {
            case "add":
                requestAdd();
                break;
            default:
                throw new UnsupportedOperationException("Cannot execute " + args[1].toLowerCase());
        }
    }

    private void requestAdd() {
        IngredientBean ingredientBean = view.askIngredient();
        controller.add(ingredientBean.getName());
    }
}
