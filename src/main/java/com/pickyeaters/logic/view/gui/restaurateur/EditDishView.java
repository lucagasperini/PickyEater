package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.virtual.VirtualShowIngredientView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public abstract class EditDishView extends VirtualShowIngredientView {
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
    protected TextField inputName;
    @FXML
    protected TextArea inputDescription;
    @FXML
    protected ComboBox<String> comboBoxCategory;

    protected final String comboBoxItemDrink;
    protected final String comboBoxItemFirst;
    protected final String comboBoxItemAppetizer;
    protected final String comboBoxItemSecond;
    protected final String comboBoxItemContour;
    protected final String comboBoxItemDessert;
    protected EditDishView(VirtualPaneView parent) {
        super("/form/restaurateur/EditDish.fxml", "RESTAURATEUR_ADDDISH", parent);
        comboBoxItemDrink = i18nGlobal("DISH_TYPE_DRINK");
        comboBoxItemFirst = i18nGlobal("DISH_TYPE_FIRST");
        comboBoxItemAppetizer = i18nGlobal("DISH_TYPE_APPETIZER");
        comboBoxItemSecond = i18nGlobal("DISH_TYPE_SECOND");
        comboBoxItemContour = i18nGlobal("DISH_TYPE_CONTOUR");
        comboBoxItemDessert = i18nGlobal("DISH_TYPE_DESSERT");

        textCategory.setText(i18n("CATEGORY"));
        textDescription.setText(i18n("DESCRIPTION"));
        textAllergens.setText(i18n("ALLERGENS"));
        textIngredients.setText(i18n("INGREDIENTS"));
        textName.setText(i18n("NAME"));
        buttonAddIngredient.setText(i18n("ADDINGREDIENT"));

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

    @FXML
    protected void clickAddIngredient(ActionEvent event) {
        AddIngredientView view = new AddIngredientView(this);
        view.show();
    }
    @FXML
    protected abstract void clickSaveChanges(ActionEvent event);
}
