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
    public AddIngredientToDBView(VirtualPaneView parent) {
        super("/form/administrator/AddIngredientToDB.fxml", "ADMINISTRATOR_ADDINGREDIENTTODB", parent);
    }

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
        showTitle();
        textIngredient.setText(i18n("INGREDIENT"));
        textSelectChildren.setText(i18n("SELECTCHILDREN"));
        textSelectChildrenExplanation.setText(i18n("SELECTCHILDREN_EXPLANATION"));
        textSelectedChildren.setText(i18n("SELECTEDCHILDREN"));
        textSelectedFather.setText(i18n("SELECTEDFATHER"));
        textSelectFather.setText(i18n("SELECTFATHER"));
        textSelectFatherExplanation.setText(i18n("SELECTFATHER_EXPLANATION"));
        buttonSave.setText(i18nGlobal("SAVECHANGES"));

    }
    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }
    @FXML
    private void clickButtonSave(ActionEvent event) {
        throw new UnsupportedOperationException();
    }
}
