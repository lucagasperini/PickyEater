package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.pickie.AddDislikedIngredientController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.IngredientBean;
import com.pickyeaters.logic.view.bean.IngredientTreeBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.virtual.VirtualShowIngredientChildView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;

import java.util.*;

public class AddDislikedIngredientView extends VirtualShowIngredientChildView {
    private final AddDislikedIngredientController controller = new AddDislikedIngredientController();
    protected AddDislikedIngredientView(VirtualPaneView parent) {
        super("/form/pickie/AddDislikedIngredient.fxml", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle("PICKY_ADDDISLIKEDINGREDIENT");
        try {
            setupTreeIngredient();
        } catch (ControllerException e) {
            showError(e);
        }
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
    private TreeView<String> treeIngredient;

    @FXML
    void clickButtonReport(ActionEvent event) {

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
            arg.put("addIngredientOptional", null);
            showParent(arg);
        }
    }

}
