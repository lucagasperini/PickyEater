package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.gui.FindRestaurantView;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class PickieHomeView extends VirtualPaneView {
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private Button buttonReviewDish;
    @FXML
    private Button buttonFindRestaurant;
    @FXML
    private Button buttonEatingPreferences;
    public PickieHomeView(MainController controller, BorderPane mainLayout) {
        super(controller, "/form/pickie/homeView.fxml", mainLayout);
        textTitle.setText(SettingsController.i18n("PICKY_GUI_HOME_VIEW_TITLE"));
        textSubtitle.setText(SettingsController.i18n("PICKY_GUI_HOME_VIEW_SUBTITLE"));
        buttonFindRestaurant.setText(SettingsController.i18n("PICKY_GUI_HOME_VIEW_FINDARESTAURANT"));
        buttonReviewDish.setText(SettingsController.i18n("PICKY_GUI_HOME_VIEW_REVIEWADISH"));
        buttonEatingPreferences.setText(SettingsController.i18n("PICKY_GUI_HOME_VIEW_PERSONALIZEEATINGPREFERENCES"));
    }

    @FXML
    protected void clickFindRestaurant(ActionEvent event) {
        FindRestaurantView findRestaurantView = new FindRestaurantView(controller, mainLayout);
        findRestaurantView.show();
    }
}
