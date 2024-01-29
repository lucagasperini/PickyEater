package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class AddDishView extends VirtualPaneView {
    @FXML
    private Button buttonBack;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private Button buttonAddIngredient;
    public AddDishView(VirtualPaneView parent) {
        super("/form/restaurateur/AddDish.fxml", parent);
    }

    @Override
    protected void setup() {
        textTitle.setText(SettingsController.i18n("ADDDISH_TITLE"));
        textSubtitle.setText(SettingsController.i18n("ADDDISH_SUBTITLE"));
        buttonBack.setText(SettingsController.i18n("BACK_TEXT"));
    }

    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }

    @FXML
    private void clickAddIngredient(ActionEvent event) {
        AddIngredientView view = new AddIngredientView(this);
        view.show();
    }
    @FXML
    private void clickSaveChanges(ActionEvent event) {

    }

    @FXML
    private void clickDeleteIngredient(ActionEvent event) {

    }
}
