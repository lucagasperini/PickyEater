package com.pickyeaters.logic.controller;

import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.view.cli.restaurateur.RestaurateurAddDishView;

import java.awt.event.ActionEvent;

public class AddDishController implements Controller{
    private int action;
    private RestaurateurAddDishView view;
    private Dish dish;
    public AddDishController()
    /*
    public void start(){
        showAddDishView();
        while(true) {
            action = getAddDishAction();
            switch (action) {
                case "1":
                    goBack();
                    break;
                case "2":
                    editDishForm
                    break;
                case "3":
                    saveDishForm
                    break;
                case "4":
                    exitByeBye
                    break;
                default:
                    throw new UnsupportedOperationException("Cannot execute " + tmp[0].toLowerCase());
            }
        }
    };

    public void editDishName(){    }
    public void editDishDescription(){}
    public void editDishType(){}
    public void addIngredient(){}
    public void deleteIngredient(String ingredientId){
        view = new RestaurateurRemoveIngredientView();
        //idkwhat
        if(really sure){
            dish.deleteIngredient(ingredientId);
        }
    }
    public void saveDish(){
        //dish bean into dish
        //dish into dish dao
        //push into db
        //show operation result
        //if operation result = successfully saved
        //goBack();
    }
    public void goBack(){
        view = new RestaurateurHomeView();
        view.show();
    }
    public void editDish(){
        view = new RestaurateurAddDishView();
        view.showDish();
    }
*/
    @Override
    public void actionPerformed(ActionEvent e) {

        String errore = "";

        // Filtro il componente che ha chiamato actionPerformed(), in base a questo eseguo azioni diverse

        if (e.getSource() == finestra.getAggiungiButton()) errore = aggiungiPersona();
        else if (e.getSource() == finestra.getModificaButton()) errore = modificaPersona();
        else if (e.getSource() == finestra.getRimuoviButton()) errore = rimuoviPersona();
    }
}
