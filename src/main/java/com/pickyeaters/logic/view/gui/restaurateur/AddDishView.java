package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.AddDishController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.model.Dish;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.restaurateur.widget.IngredientListItemWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.Map;

public class AddDishView extends VirtualPaneView {
    AddDishController controller;
    @FXML
    private Button buttonBack;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private Button buttonAddIngredient;

    @FXML
    private Text textName;
    @FXML
    private Text textIngredients;
    @FXML
    private Text textAllergens;
    @FXML
    private Text textDescription;
    @FXML
    private Text textCategory;
    @FXML
    private Button buttonSave;
    @FXML
    private VBox vboxIngredient;

    @FXML
    private TextField inputName;
    @FXML
    private TextArea inputDescription;
    @FXML
    private ComboBox<String> comboBoxCategory;
    private LinkedList<IngredientListItemWidget> ingredientListItemWidgets = new LinkedList<>();
    public AddDishView(AddDishController controller, VirtualPaneView parent) {
        super("/form/restaurateur/AddDish.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {
        if(arg != null) {
            setupAddIngredient(arg.get("addIngredient"));
            setupRemoveIngredient(arg.get("removeIngredient"));
        }
        textTitle.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_SUBTITLE"));
        buttonBack.setText(SettingsController.i18n("BACK"));
        buttonSave.setText(SettingsController.i18n("SAVECHANGES"));
        textCategory.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_CATEGORY"));
        textDescription.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_DESCRIPTION"));
        textAllergens.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_ALLERGENS"));
        textIngredients.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_INGREDIENTS"));
        textName.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_NAME"));
        buttonAddIngredient.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_ADDINGREDIENT"));

        comboBoxCategory.getItems().clear();
        comboBoxCategory.getItems().addAll(
                Dish.TYPE_DRINK,
                Dish.TYPE_APPETIZER,
                Dish.TYPE_FIRST,
                Dish.TYPE_CONTOUR,
                Dish.TYPE_SECOND,
                Dish.TYPE_DESSERT
        );
    }

    private void setupAddIngredient(String name) {
        if(name != null) {
            ingredientListItemWidgets.add(new IngredientListItemWidget(this, name));
            vboxIngredient.getChildren().add(ingredientListItemWidgets.getLast().getRoot());
        }
    }

    private void setupRemoveIngredient(String name) {
        for(IngredientListItemWidget widget : ingredientListItemWidgets) {
            if(widget.hasName(name)) {
                vboxIngredient.getChildren().remove(widget.getRoot());
                return;
            }
        }
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
        DishBean dishBean = new DishBean(
                inputName.getText(),
                inputDescription.getText(),
                comboBoxCategory.getValue()
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

    @FXML
    private void clickDeleteIngredient(ActionEvent event) {

    }
}
