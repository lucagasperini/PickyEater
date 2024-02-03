package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.AddDishController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
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
        showTitle("RESTAURATEUR_ADDDISH");

        buttonSave.setText(SettingsController.i18n("SAVECHANGES"));
    }

    @FXML
    protected void clickSaveChanges(ActionEvent event) {
        try {
            DishBean dishBean = new DishBean();
            dishBean.setName(inputName.getText());
            dishBean.setDescription(inputDescription.getText());
            dishBean.setCategory(getCurrentComboBoxItem());

            for(IngredientListItemWidget widget : ingredientListItemWidgets) {
                dishBean.addIngredient(new DishIngredientBean(widget.getName()));
            }

            controller.add(dishBean);
            showParent();
        } catch (ControllerException | BeanException ex) {
            showError(ex);
        }
    }

}
