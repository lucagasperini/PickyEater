package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.AddIngredientController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.IngredientBean;
import com.pickyeaters.logic.view.bean.IngredientTreeBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.*;

public class AddIngredientView extends VirtualPaneView {
    @FXML
    private Text textIngredient;
    @FXML
    private Text textAllergens;
    @FXML
    private Text textCookingMethod;
    @FXML
    private Text textReligiousNeeds;
    @FXML
    private Text textOptionality;
    @FXML
    private Text textIngredientSelect;
    @FXML
    private Text textIngredientSelectExplanation;
    @FXML
    private Text textAllergensExplanation;
    @FXML
    private Text textAllergensSelectExplanation;
    @FXML
    private Text textReportMissingIngredientQuestion;
    @FXML
    private Text textReligiousNeedsIngredientIs;
    @FXML
    private CheckBox checkboxReligiousNeedsHalal;
    @FXML
    private CheckBox checkboxReligiousNeedsKosher;
    @FXML
    private Button buttonReport;
    @FXML
    private Button buttonSave;
    @FXML
    private CheckBox checkBoxCooked;
    @FXML
    private CheckBox checkBoxOptional;

    @FXML
    private TreeView<String> treeIngredient;

    AddIngredientController controller;
    public AddIngredientView(AddIngredientController controller, VirtualPaneView parent) {
        super("/form/restaurateur/AddIngredient.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle("RESTAURATEUR_ADDINGREDIENT");
        textIngredient.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_INGREDIENT"));
        textIngredientSelect.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_INGREDIENT_SELECT"));
        textIngredientSelectExplanation.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_INGREDIENT_SELECTEXPLANATION"));
        textAllergens.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_ALLERGENS"));
        textAllergensExplanation.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_ALLERGENS_EXPLANATION"));
        textAllergensSelectExplanation.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_ALLERGENS_SELECTEXPLANATION"));
        textCookingMethod.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_COOKINGMETHOD"));
        textReligiousNeeds.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_RELIGIOUSNEEDS"));
        textReligiousNeedsIngredientIs.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_RELIGIOUSNEEDS_INGREDIENTIS"));
        checkboxReligiousNeedsHalal.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_RELIGIOUSNEEDS_HALAL"));
        checkboxReligiousNeedsKosher.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_RELIGIOUSNEEDS_KOSHER"));
        textOptionality.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_OPTIONALITY"));
        textReportMissingIngredientQuestion.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_REPORTMISSINGINGREDIENT_QUESTION"));
        buttonReport.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_REPORTMISSINGINGREDIENT"));
        buttonSave.setText(SettingsController.i18n("SAVECHANGES"));

        try {
            setupTreeIngredient();
        } catch (ControllerException e) {
            showError(e);
        }

    }

    private void setupTreeIngredient() throws ControllerException {
        TreeItem<String> treeIngredientRoot = new TreeItem<>(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_INGREDIENT"));

        List<IngredientTreeBean> treeBeanList = controller.getIngrendientTreeList();
        for(IngredientTreeBean tree : treeBeanList) {
            Deque<IngredientBean> beanStack = new LinkedList<>();
            Deque<TreeItem<String>> treeItemStack = new LinkedList<>();
            beanStack.push(tree.getRoot());
            TreeItem<String> subRoot = new TreeItem<>(tree.getRoot().getName());
            treeIngredientRoot.getChildren().add(subRoot);
            treeItemStack.push(subRoot);
            while(!beanStack.isEmpty()) {
                IngredientBean bean = beanStack.pop();
                TreeItem<String> item = treeItemStack.pop();
                for(IngredientBean i : bean.getChildList()) {
                    beanStack.push(i);
                    TreeItem<String> childItem = new TreeItem<>(i.getName());
                    treeItemStack.push(childItem);
                    item.getChildren().add(childItem);
                }
            }
        }

        treeIngredient.setRoot(treeIngredientRoot);
    }
    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }
    @FXML
    private void clickButtonReport(ActionEvent event) {
        throw new UnsupportedOperationException();
    }
    @FXML
    private void clickButtonSave(ActionEvent event) {
        TreeItem<String> selectedItem = treeIngredient.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            showParent();
        } else {
            Map<String, String> arg = new HashMap<>();
            arg.put("addIngredientName", selectedItem.getValue());
            arg.put("addIngredientCooked", checkBoxCooked.isSelected() ? "true" : "false");
            arg.put("addIngredientOptional", checkBoxOptional.isSelected() ? "true" : "false");
            showParent(arg);
        }
    }
}
