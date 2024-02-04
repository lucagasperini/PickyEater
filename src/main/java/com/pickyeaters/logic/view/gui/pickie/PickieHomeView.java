package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Map;

public class PickieHomeView extends VirtualPaneView {

    @FXML
    private Button buttonReviewDish;
    @FXML
    private Button buttonFindRestaurant;
    @FXML
    private Button buttonEatingPreferences;
    public PickieHomeView(VirtualPaneView parent) {
        super("/form/pickie/Home.fxml", parent);
    }

    @FXML
    private void clickFindRestaurant(ActionEvent event) {
        FindRestaurantView view = new FindRestaurantView(this);
        view.show();
    }

    @FXML
    private void clickButtonReviewDish(ActionEvent event) {
        ReviewDishView view = new ReviewDishView(this);
        view.show();
    }

    @FXML
    private void clickButtonEatingPreferences(ActionEvent event) {
        EatingPreferencesView view = new EatingPreferencesView(this);
        view.show();
    }



    @Override
    protected void setup(Map<String, String> arg) {
        showTitle("PICKY_HOME");
        buttonFindRestaurant.setText(SettingsController.i18n("PICKY_HOME_FINDRESTAURANT"));
        buttonReviewDish.setText(SettingsController.i18n("PICKY_HOME_REVIEWADISH"));
        buttonEatingPreferences.setText(SettingsController.i18n("PICKY_HOME_PERSONALIZEEATINGPREFERENCES"));
    }
}
