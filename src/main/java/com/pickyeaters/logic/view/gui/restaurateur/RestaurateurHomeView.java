package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class RestaurateurHomeView extends VirtualPaneView {
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private Button buttonRestaurant;
    @FXML
    private Button buttonMenu;
    public RestaurateurHomeView(MainController controller, BorderPane mainLayout) {
        super(controller, "/form/restaurateur/homeView.fxml", mainLayout);
        textTitle.setText(SettingsController.i18n("RESTAURATEUR_GUI_HOME_VIEW_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURATEUR_GUI_HOME_VIEW_SUBTITLE"));
        buttonMenu.setText(SettingsController.i18n("RESTAURATEUR_GUI_HOME_VIEW_PROVIDEMENUDETAILS"));
        buttonRestaurant.setText(SettingsController.i18n("RESTAURATEUR_GUI_HOME_VIEW_PROVIDERESTAURANTDETAILS"));
    }
}
