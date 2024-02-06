package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.restaurateur.AddIngredientController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.IngredientBean;
import com.pickyeaters.logic.view.bean.IngredientTreeBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.virtual.VirtualShowIngredientChildView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.*;

public class AddIngredientView extends VirtualShowIngredientChildView {
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
    private CheckBox checkBoxOptional;

    private final AddIngredientController controller = new AddIngredientController();
    public AddIngredientView(VirtualPaneView parent) {
        super("/form/restaurateur/AddIngredient.fxml", parent);
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

    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }
    @FXML
    private void clickButtonReport(ActionEvent event) {
        throw new UnsupportedOperationException();
    }
    @FXML
    void clickButtonSave(ActionEvent event) {
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
