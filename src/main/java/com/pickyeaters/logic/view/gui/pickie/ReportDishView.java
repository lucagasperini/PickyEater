package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.ReportDishController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Map;

public class ReportDishView extends VirtualPaneView {
    private final ReportDishController controller = new ReportDishController();
    public ReportDishView(VirtualPaneView parent) {
        super("/form/pickie/ReportDish.fxml", "REPORTDISH", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle();
    }


    @FXML
    private Button buttonSave;

    @FXML
    private CheckBox checkboxListedNotUsedIngredient;

    @FXML
    private CheckBox checkboxNotListedUsedIngredient;

    @FXML
    private TextField inputIngredient;

    @FXML
    private Text textExplanation;

    @FXML
    private Text textIngredient;

    @FXML
    void clickButtonSave(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

}
