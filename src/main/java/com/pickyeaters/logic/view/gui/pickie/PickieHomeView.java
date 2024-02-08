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
        super("/form/pickie/Home.fxml", "PICKY_HOME", parent);
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
        showTitle();
        hideBack();
        buttonFindRestaurant.setText(i18n("FINDRESTAURANT"));
        buttonReviewDish.setText(i18n("REVIEWADISH"));
        buttonEatingPreferences.setText(i18n("PERSONALIZEEATINGPREFERENCES"));
    }
}
