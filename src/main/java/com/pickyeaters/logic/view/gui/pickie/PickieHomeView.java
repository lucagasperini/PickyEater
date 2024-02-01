package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.pickie.PickieController;
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
    private final PickieController controller;
    public PickieHomeView(PickieController controller, VirtualPaneView parent) {
        super("/form/pickie/Home.fxml", parent);
        this.controller = controller;
    }

    @FXML
    private void clickFindRestaurant(ActionEvent event) {
        FindRestaurantView view = new FindRestaurantView(
                controller.getFindRestaurant(),
                this
        );
        view.show();
    }

    @FXML
    private void clickButtonReviewDish(ActionEvent event) {
        ReviewDishView view = new ReviewDishView(
                controller.getReviewDish(),
                this
        );
        view.show();
    }

    @FXML
    private void clickButtonEatingPreferences(ActionEvent event) {
        EatingPreferencesView view = new EatingPreferencesView(
                controller.getEatingPreferences(),
                this
        );
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
