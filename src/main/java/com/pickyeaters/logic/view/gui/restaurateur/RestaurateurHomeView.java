package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.RestaurateurController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.Map;

public class RestaurateurHomeView extends VirtualPaneView {
    private RestaurateurController controller;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private Button buttonRestaurantDetails;
    @FXML
    private Button buttonMenuDetails;
    public RestaurateurHomeView(RestaurateurController controller, VirtualPaneView parent) {
        super("/form/restaurateur/Home.fxml", parent);
        this.controller = controller;
    }

    @FXML
    private void clickButtonMenuDetails(ActionEvent event) {
        MenuDetailsView view = new MenuDetailsView(
                controller.getMenuDetails(),
                this
        );
        view.show();
    }

    @FXML
    private void clickButtonRestaurantDetails(ActionEvent event) {
        RestaurantDetailsView view = new RestaurantDetailsView(
                controller.getRestaurantDetails(),
                this
        );
        view.show();
    }

    @Override
    protected void setup(Map<String, String> arg) {
        textTitle.setText(SettingsController.i18n("RESTAURATEUR_HOME_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURATEUR_HOME_SUBTITLE"));
        buttonMenuDetails.setText(SettingsController.i18n("RESTAURATEUR_HOME_MANAGEMENUDETAILS"));
        buttonRestaurantDetails.setText(SettingsController.i18n("RESTAURATEUR_HOME_MANAGERESTAURANTDETAILS"));
    }
}
