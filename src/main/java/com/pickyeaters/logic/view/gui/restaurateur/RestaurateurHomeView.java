package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Map;

public class RestaurateurHomeView extends VirtualPaneView {
    @FXML
    private Button buttonRestaurantDetails;
    @FXML
    private Button buttonMenuDetails;
    public RestaurateurHomeView(VirtualPaneView parent) {
        super("/form/restaurateur/Home.fxml", parent);
    }

    @FXML
    private void clickButtonMenuDetails(ActionEvent event) {
        MenuDetailsView view = new MenuDetailsView(this);
        view.show();
    }

    @FXML
    private void clickButtonRestaurantDetails(ActionEvent event) {
        RestaurantDetailsView view = new RestaurantDetailsView(this);
        view.show();
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle("RESTAURATEUR_HOME");
        hideBack();
        buttonMenuDetails.setText(SettingsController.i18n("RESTAURATEUR_HOME_MANAGEMENUDETAILS"));
        buttonRestaurantDetails.setText(SettingsController.i18n("RESTAURATEUR_HOME_MANAGERESTAURANTDETAILS"));
    }
}
