package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.UpdateDishController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.restaurateur.widget.IngredientListItemWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Map;

public class UpdateDishView extends EditDishView {
    UpdateDishController controller;
    private DishBean dishBean;
    public UpdateDishView(UpdateDishController controller, VirtualPaneView parent, String dishID) {
        super(parent);
        this.controller = controller;
        try {
            dishBean = controller.get(dishID);
        } catch (ControllerException e) {
            showError(e);
        }
        for(String s : dishBean.getIngredientList()) {
            setupAddIngredient(s);
        }
    }

    @Override
    protected void setup(Map<String, String> arg) {
        if(arg != null) {
            setupAddIngredient(arg.get("addIngredient"));
            setupRemoveIngredient(arg.get("removeIngredient"));
        }
        showTitle("RESTAURATEUR_UPDATEDISH");
        buttonSave.setText(SettingsController.i18n("SAVECHANGES"));

        inputName.setText(dishBean.getName());
        inputDescription.setText(dishBean.getDescription());

        comboBoxCategory.setValue(SettingsController.i18n("DISH_TYPE_" + dishBean.getCategory()));
    }

    @FXML
    protected void clickSaveChanges(ActionEvent event) {
        DishBean tmp = new DishBean(
                dishBean.getID(),
                inputName.getText(),
                inputDescription.getText(),
                getCurrentComboBoxItem()
        );

        for(IngredientListItemWidget widget : ingredientListItemWidgets) {
            tmp.addIngredient(widget.getName());
        }

        try {
            controller.update(tmp);
            showParent();
        } catch (ControllerException ex) {
            showError(ex);
        }
    }

}
