package com.pickyeaters.logic.view.gui.restaurateur.widget;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.VirtualWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class IngredientListItemWidget extends VirtualWidget {
    @FXML
    private Button buttonDeleteIngredient;
    @FXML
    private Text textIngredient;
    public IngredientListItemWidget(VirtualPaneView parent, String name) {
        super("/form/restaurateur/widget/IngredientListItemWidget.fxml", parent);
        textIngredient.setText(name);
        buttonDeleteIngredient.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_REMOVEINGREDIENT"));
    }

    @FXML
    private void clickButtonDeleteIngredient(ActionEvent actionEvent) {
        Map<String, String> arg = new HashMap<>();
        arg.put("removeIngredient", textIngredient.getText());
        toParent(arg);
    }

    public boolean hasName(String name) {
        return getName().equals(name);
    }

    public String getName() {
        return textIngredient.getText();
    }
}