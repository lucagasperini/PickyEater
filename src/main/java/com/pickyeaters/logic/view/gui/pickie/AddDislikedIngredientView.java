package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;

import java.util.Map;

public class AddDislikedIngredientView extends VirtualPaneView {
    protected AddDislikedIngredientView(VirtualPaneView parent) {
        super("/form/pickie/AddDislikedIngredient.fxml", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle("PICKY_ADDDISLIKEDINGREDIENT");
    }


    @FXML
    private Button buttonReport;

    @FXML
    private Button buttonSave;

    @FXML
    private CheckBox checkboxCookingMethodCooked;

    @FXML
    private CheckBox checkboxCookingMethodRaw;

    @FXML
    private Text textCookingMethod;

    @FXML
    private Text textReportMissingIngredientQuestion;

    @FXML
    private Text textSelect;

    @FXML
    private TreeView<?> treeIngredient;

    @FXML
    void clickButtonReport(ActionEvent event) {

    }

    @FXML
    void clickButtonSave(ActionEvent event) {

    }

}
