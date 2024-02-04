package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.restaurateur.widget.IngredientListItemWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class EditDishView extends VirtualPaneView {
    @FXML
    protected Button buttonAddIngredient;

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
    protected VBox vboxIngredient;

    @FXML
    protected TextField inputName;
    @FXML
    protected TextArea inputDescription;
    @FXML
    protected ComboBox<String> comboBoxCategory;
    protected LinkedList<IngredientListItemWidget> ingredientListItemWidgets = new LinkedList<>();
    protected List<DishIngredientBean> ingredientBeanList = new ArrayList<>();
    protected final String comboBoxItemDrink;
    protected final String comboBoxItemFirst;
    protected final String comboBoxItemAppetizer;
    protected final String comboBoxItemSecond;
    protected final String comboBoxItemContour;
    protected final String comboBoxItemDessert;
    protected EditDishView(VirtualPaneView parent) {
        super("/form/restaurateur/EditDish.fxml", parent);
        comboBoxItemDrink = SettingsController.i18n("DISH_TYPE_DRINK");
        comboBoxItemFirst = SettingsController.i18n("DISH_TYPE_FIRST");
        comboBoxItemAppetizer = SettingsController.i18n("DISH_TYPE_APPETIZER");
        comboBoxItemSecond = SettingsController.i18n("DISH_TYPE_SECOND");
        comboBoxItemContour = SettingsController.i18n("DISH_TYPE_CONTOUR");
        comboBoxItemDessert = SettingsController.i18n("DISH_TYPE_DESSERT");

        textCategory.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_CATEGORY"));
        textDescription.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_DESCRIPTION"));
        textAllergens.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_ALLERGENS"));
        textIngredients.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_INGREDIENTS"));
        textName.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_NAME"));
        buttonAddIngredient.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_ADDINGREDIENT"));

        comboBoxCategory.getItems().addAll(
                comboBoxItemDrink,
                comboBoxItemAppetizer,
                comboBoxItemFirst,
                comboBoxItemContour,
                comboBoxItemSecond,
                comboBoxItemDessert
        );
    }

    protected String getCurrentComboBoxItem() {
        String cur = comboBoxCategory.getValue();

        if(cur.equals(comboBoxItemDrink))
            return Dish.TYPE_DRINK;
        else if(cur.equals(comboBoxItemAppetizer))
            return Dish.TYPE_APPETIZER;
        else if(cur.equals(comboBoxItemFirst))
            return Dish.TYPE_FIRST;
        else if(cur.equals(comboBoxItemContour))
            return Dish.TYPE_CONTOUR;
        else if(cur.equals(comboBoxItemSecond))
            return Dish.TYPE_SECOND;
        else if(cur.equals(comboBoxItemDessert))
            return Dish.TYPE_DESSERT;
        else
            return "";
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
    protected abstract void clickSaveChanges(ActionEvent event);
}
