package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.AddDishController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.AppData;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.bean.EditDishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Map;

public class AddDishView extends EditDishView {
    private final AddDishController controller = new AddDishController();

    public AddDishView(VirtualPaneView parent) {
        super(parent);
        comboBoxCategory.getSelectionModel().select(0);
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
        showTitle("RESTAURATEUR_ADDDISH");

        buttonSave.setText(SettingsController.i18n("SAVECHANGES"));
    }

    @FXML
    protected void clickSaveChanges(ActionEvent event) {
        try {
            EditDishBean dishBean = new EditDishBean(
                    inputName.getText(),
                    inputDescription.getText(),
                    getCurrentComboBoxItem()
            );

            for(DishIngredientBean ingredientBean : ingredientBeanList) {
                dishBean.getIngredientList().add(ingredientBean);
            }

            controller.add(dishBean, AppData.getInstance().getRestaurantID());
            showParent();
        } catch (ControllerException | BeanException ex) {
            showError(ex);
        }
    }

}
