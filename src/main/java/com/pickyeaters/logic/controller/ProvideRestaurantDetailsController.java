package com.pickyeaters.logic.controller;

import com.pickyeaters.logic.model.Restaurant;
import com.pickyeaters.logic.model.Restaurateur;
import com.pickyeaters.logic.view.View;
import com.pickyeaters.logic.view.cli.OperationFeedbackView;
import com.pickyeaters.logic.view.cli.restaurateur.RestaurateurHomeView;
import com.pickyeaters.logic.view.cli.AskAgainView;
import com.pickyeaters.logic.view.cli.restaurateur.RestaurateurProvideRestaurantDetailsView;

import java.awt.event.ActionEvent;

public class ProvideRestaurantDetailsController implements Controller {
    private View view;
    private RestaurateurProvideRestaurantDetailsView restaurateurProvideRestaurantDetailsView;
    private Restaurant restaurant;
    private Restaurateur restaurateur;

    //nel costruttore, inizializzo gli elementi grafici: li mostro e li collego alle azioni
    public ProvideRestaurantDetailsController (RestaurateurProvideRestaurantDetailsView restaurateurProvideRestaurantDetailsView, Restaurant restaurant, Restaurateur restaurateur){
        this.restaurateurProvideRestaurantDetailsView = restaurateurProvideRestaurantDetailsView;
        this.restaurant = restaurant;
        this.restaurateur = restaurateur;

        //Aggiungo l'action Listener ai vari bottoni dell' app

        restaurateurProvideRestaurantDetailsView.getGoBackButton().addActionListener(this);
        restaurateurProvideRestaurantDetailsView.getSaveChangesButton().addActionListener(this);

        restaurateurProvideRestaurantDetailsView.getTabellaRubrica().setModel(rubrica);
        restaurateurProvideRestaurantDetailsView.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restaurateurProvideRestaurantDetailsView.getGoBackButton()) {
            goBack();
        }
        else if (e.getSource() == restaurateurProvideRestaurantDetailsView.getSaveChangesButton()) {
            saveChanges();
        }
    }

    //
    private void goBack() {
        view = new RestaurateurHomeView();
        view.show();
    }
    private void saveChanges() {
        try{
            restaurant = restaurantBean;
            restaurateur = restaurauterBean;
            restaurantDAO = restaurant;
            restaurateurDAO = restaurateur;
            save(restaurantDAO);
            save(restaurateurDAO);
        }
        catch (Exception saveError){        //PSEUDOSCEMOCODICE
            view = new AskAgainView("unsuccessful save");
        }
        finally{
            view = new OperationFeedbackView("successful saving", new RestaurateurHomeView());
            view.show();
        }
    }
}
