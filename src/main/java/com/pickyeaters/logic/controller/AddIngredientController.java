package com.pickyeaters.logic.controller;

import com.pickyeaters.logic.view.cli.pickie.PickieAddIngredientView;

public class AddIngredientController implements Controller{
    public void start(){
        //TODO: AddIngredientController deve mostrare 4 view diverse, dipendenti sia dall'utente che lo usa che dal fatto che sia GUI o CLI;
        PickieAddIngredientView view = new PickieAddIngredientView();
    };
}
