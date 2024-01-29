package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.MenuDetailsController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.restaurateur.widget.DishWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuDetailsView extends VirtualPaneView {
    public MenuDetailsView(MenuDetailsController controller, VirtualPaneView parent) {
        super("/form/restaurateur/MenuDetails.fxml", parent);
    }

    @FXML
    private Button buttonBack;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private VBox boxDishList;
    @FXML
    private Button buttonAddDish;
    @Override
    protected void setup() {
        textTitle.setText(SettingsController.i18n("MENUDETAILS_TITLE"));
        textSubtitle.setText(SettingsController.i18n("MENUDETAILS_SUBTITLE"));
        buttonBack.setText(SettingsController.i18n("BACK_TEXT"));
        /*
        for(int i = 0; i < 10; i++) {
            DishWidget dishWidget = new DishWidget();
            boxDishList.getChildren().add(dishWidget.getRoot());
        }
         */
    }

    @FXML
    private void clickButtonAddDish(ActionEvent event) {
        AddDishView view = new AddDishView(this);
        view.show();
    }

    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }

}
