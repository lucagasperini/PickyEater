package com.pickyeaters.logic.view.gui.administrator;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.administrator.AddIngredientToDBController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.Map;

public class AddIngredientToDBView extends VirtualPaneView {
    private final AddIngredientToDBController controller = new AddIngredientToDBController();
    public AddIngredientToDBView(VirtualPaneView parent) { super("/form/administrator/AddIngredientToDB.fxml", parent); }

    @FXML
    private Text textIngredient;
    @FXML
    private Text textSelectChildren;
    @FXML
    private Text textSelectChildrenExplanation;
    @FXML
    private Text textSelectedChildren;
    @FXML
    private Text textSelectedFather;
    @FXML
    private Text textSelectFather;
    @FXML
    private Text textSelectFatherExplanation;
    @FXML
    private TextField inputIngredient;
    @FXML
    private Button buttonSave;
    @FXML
    private ListView<String> listFather;
    @FXML
    private ListView<String> listChildren;
    @FXML
    private TreeView<String> treeIngredientChildren;
    @FXML
    private TreeView<String> treeIngredientFather;

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle("ADMINISTRATOR_ADDINGREDIENTTODB");
        textIngredient.setText(SettingsController.i18n("ADMINISTRATOR_ADDINGREDIENTTODB_INGREDIENT"));
        textSelectChildren.setText(SettingsController.i18n("ADMINISTRATOR_ADDINGREDIENTTODB_SELECTCHILDREN"));
        textSelectChildrenExplanation.setText(SettingsController.i18n("ADMINISTRATOR_ADDINGREDIENTTODB_SELECTCHILDREN_EXPLANATION"));
        textSelectedChildren.setText(SettingsController.i18n("ADMINISTRATOR_ADDINGREDIENTTODB_SELECTEDCHILDREN"));
        textSelectedFather.setText(SettingsController.i18n("ADMINISTRATOR_ADDINGREDIENTTODB_SELECTEDFATHER"));
        textSelectFather.setText(SettingsController.i18n("ADMINISTRATOR_ADDINGREDIENTTODB_SELECTFATHER"));
        textSelectFatherExplanation.setText(SettingsController.i18n("ADMINISTRATOR_ADDINGREDIENTTODB_SELECTFATHER_EXPLANATION"));
        buttonSave.setText(SettingsController.i18n("ADMINISTRATOR_ADDINGREDIENTTODB_SAVECHANGES"));

        try {
            //setupTreeIngredient();
        } catch (ControllerException e) {
            showError(e);
        }

    }
    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }
    @FXML
    private void clickButtonSave(ActionEvent event) {
    }
}
