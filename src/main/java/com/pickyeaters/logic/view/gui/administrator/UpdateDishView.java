package com.pickyeaters.logic.view.gui.administrator;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.administrator.UpdateDishController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.AppData;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.bean.EditDishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.restaurateur.AddIngredientView;
import com.pickyeaters.logic.view.gui.widget.IngredientListItemWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class UpdateDishView extends VirtualPaneView {
    private final UpdateDishController controller = new UpdateDishController();

    @FXML
    protected Text textName;
    @FXML
    protected Text textIngredients;
    @FXML
    protected Text textAllergens;
    @FXML
    protected Text textDescription;
    @FXML
    protected Text textCategory;
    @FXML
    protected Button buttonSave;
    @FXML
    protected Button buttonAddIngredient;
    @FXML
    protected VBox vboxIngredient;
    @FXML
    protected Text dishName;
    @FXML
    protected Text dishDescription;
    @FXML
    protected Text dishCategory;

    protected LinkedList<IngredientListItemWidget> ingredientListItemWidgets = new LinkedList<>();
    protected List<DishIngredientBean> ingredientBeanList = new ArrayList<>();
    protected UpdateDishView(VirtualPaneView parent) {
        super("/form/administrator/EditDish.fxml", "ADMINISTRATOR_UPDATEDISH", parent);
        showTitle();
        textName.setText(i18n("NAME"));
        textIngredients.setText(i18n("INGREDIENTS"));
        textAllergens.setText(i18n("ALLERGENS"));
        textDescription.setText(i18n("DESCRIPTION"));
        textCategory.setText(i18n("CATEGORY"));
        buttonSave.setText(i18nGlobal("SAVECHANGES"));
        buttonAddIngredient.setText(i18n("ADDINGREDIENT"));
    }
    protected void setupAddIngredient(DishIngredientBean bean) {
        ingredientBeanList.add(bean);
        ingredientListItemWidgets.add(new IngredientListItemWidget(this, bean));
        vboxIngredient.getChildren().add(ingredientListItemWidgets.getLast().getRoot());
    }
    protected void setupAddIngredient(String name, String cooked, String optional) {
        if(name != null) {
            boolean isCooked = false;
            boolean isOptional = false;
            if(cooked != null) {
                isCooked = cooked.equals("true");
            }
            if(optional != null) {
                isOptional = optional.equals("true");
            }

            setupAddIngredient(new DishIngredientBean(name, isCooked, isOptional));
        }
    }
    protected void setupRemoveIngredient(String name) {
        for(IngredientListItemWidget widget : ingredientListItemWidgets) {
            if(widget.getBean().getName().equals(name)) {
                vboxIngredient.getChildren().remove(widget.getRoot());
                ingredientListItemWidgets.remove(widget);
                return;
            }
        }
    }
    @FXML
    private void clickAddIngredient(ActionEvent event) {
        AddIngredientView view = new AddIngredientView(this);
        view.show();
    }
    @FXML
    protected void clickSaveChanges(ActionEvent event) {
        /*try {
            EditDishBean tmp = new EditDishBean(
                    dishName.getText(),
                    dishDescription.getText(),
                    dishCategory.getText()
            );

            for(IngredientListItemWidget widget : ingredientListItemWidgets) {
                tmp.getIngredientList().add(widget.getBean());
            }

            //controller.update(tmp, dishName, AppData.getInstance().getRestaurantID());
            showParent();
        } catch (ControllerException | BeanException ex) {
            showError(ex);
        }*/
    }
}
