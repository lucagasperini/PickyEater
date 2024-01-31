package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.AddDishController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.restaurateur.widget.IngredientListItemWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Map;

public class AddDishView extends EditDishView {
    AddDishController controller;

    public AddDishView(AddDishController controller, VirtualPaneView parent) {
        super(parent);
        this.controller = controller;
        comboBoxCategory.getSelectionModel().select(0);
    }
    @Override
    protected void setup(Map<String, String> arg) {
        if(arg != null) {
            setupAddIngredient(arg.get("addIngredient"));
            setupRemoveIngredient(arg.get("removeIngredient"));
        }
        textTitle.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_SUBTITLE"));

        buttonSave.setText(SettingsController.i18n("SAVECHANGES"));
    }

    @FXML
    protected void clickSaveChanges(ActionEvent event) {
        DishBean dishBean = new DishBean(
                inputName.getText(),
                inputDescription.getText(),
                getCurrentComboBoxItem()
        );

        for(IngredientListItemWidget widget : ingredientListItemWidgets) {
            dishBean.addIngredient(widget.getName());
        }
        try {
            controller.add(dishBean);
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }

        showParent();
    }

}
