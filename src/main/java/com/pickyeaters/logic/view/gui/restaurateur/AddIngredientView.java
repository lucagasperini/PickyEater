package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
    private Text textCookingMethodCanBeCooked;
    @FXML
    private Text textCookingMethodIsCooked;
    @FXML
    private Text textReligiousNeedsIngredientIs;
    @FXML
    private CheckBox checkboxReligiousNeedsHalal;
    @FXML
    private CheckBox checkboxReligiousNeedsKosher;
    @FXML
    private Text textOptionalityQuestion;
    @FXML
    private Button buttonReport;
    @FXML
    private Button buttonSave;

    @FXML
    private TreeView<String> treeIngredient;
    public AddIngredientView(VirtualPaneView parent) {
        super("/form/restaurateur/AddIngredient.fxml", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        textTitle.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_SUBTITLE"));
        textIngredient.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_INGREDIENT"));
        textIngredientSelect.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_INGREDIENT_SELECT"));
        textIngredientSelectExplanation.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_INGREDIENT_SELECTEXPLANATION"));
        textAllergens.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_ALLERGENS"));
        textAllergensExplanation.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_ALLERGENS_EXPLANATION"));
        textAllergensSelectExplanation.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_ALLERGENS_SELECTEXPLANATION"));
        textCookingMethod.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_COOKINGMETHOD"));
        textCookingMethodCanBeCooked.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_COOKINGMETHOD_CANBECOOKED"));
        textCookingMethodIsCooked.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_COOKINGMETHOD_ISCOOKED"));
        textReligiousNeeds.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_RELIGIOUSNEEDS"));
        textReligiousNeedsIngredientIs.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_RELIGIOUSNEEDS_INGREDIENTIS"));
        checkboxReligiousNeedsHalal.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_RELIGIOUSNEEDS_HALAL"));
        checkboxReligiousNeedsKosher.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_RELIGIOUSNEEDS_KOSHER"));
        textOptionality.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_OPTIONALITY"));
        textOptionalityQuestion.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_OPTIONALITY_QUESTION"));
        textReportMissingIngredientQuestion.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_REPORTMISSINGINGREDIENT_QUESTION"));
        buttonReport.setText(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_REPORTMISSINGINGREDIENT"));
        buttonSave.setText(SettingsController.i18n("SAVECHANGES"));

        try {
            setupTreeIngredient();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setupTreeIngredient() throws DAOException {
        TreeItem<String> treeIngredientRoot = new TreeItem<>(SettingsController.i18n("RESTAURATEUR_ADDINGREDIENT_INGREDIENT"));

        IngredientDAO.IngredientForest forest = IngredientDAO.getInstance().getAll();
        for(IngredientDAO.IngredientTree tree : forest.getTreeList()) {
            Deque<IngredientDAO.IngredientNode> nodeStack = new LinkedList<>();
            Deque<TreeItem<String>> treeItemStack = new LinkedList<>();
            nodeStack.push(tree.getRoot());
            TreeItem<String> subRoot = new TreeItem<>(tree.getRoot().toString());
            treeIngredientRoot.getChildren().add(subRoot);
            treeItemStack.push(subRoot);

            while(!nodeStack.isEmpty()) {
                IngredientDAO.IngredientNode node = nodeStack.pop();
                TreeItem<String> item = treeItemStack.pop();
                for (IngredientDAO.IngredientNode i : node.getChild()) {
                    nodeStack.push(i);
                    TreeItem<String> childItem = new TreeItem<>(i.toString());
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
            arg.put("addIngredient", selectedItem.getValue());
            showParent(arg);
        }
    }

    @FXML
    private void clickCheckboxReligiousNeedsHalal(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void clickCheckboxReligiousNeedsKosher(ActionEvent event) {
        throw new UnsupportedOperationException();
    }
}
