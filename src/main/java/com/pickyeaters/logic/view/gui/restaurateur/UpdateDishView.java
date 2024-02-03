package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.UpdateDishController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.bean.EditDishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.restaurateur.widget.IngredientListItemWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Map;

public class UpdateDishView extends EditDishView {
    private UpdateDishController controller;
    private EditDishBean dishBean;
    public UpdateDishView(UpdateDishController controller, VirtualPaneView parent, String name) {
        super(parent);
        this.controller = controller;
        try {
            dishBean = controller.get(name);
            for(DishIngredientBean s : dishBean.getIngredientList()) {
                setupAddIngredient(s);
            }
        }catch (ControllerException | BeanException e) {
            showError(e);
        }
    }

    @Override
    protected void setup(Map<String, String> arg) {
        if(arg != null) {
            setupAddIngredient(
                    arg.get("addIngredientName"),
                    arg.get("addIngredientCooked"),
                    arg.get("addIngredientOptional")
            );
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
        try {
            EditDishBean tmp = new EditDishBean(
                    inputName.getText(),
                    inputDescription.getText(),
                    getCurrentComboBoxItem()
            );

            for(IngredientListItemWidget widget : ingredientListItemWidgets) {
                tmp.getIngredientList().add(widget.getBean());
            }

            controller.update(tmp);
            showParent();
        } catch (ControllerException | BeanException ex) {
            showError(ex);
        }
    }

}
