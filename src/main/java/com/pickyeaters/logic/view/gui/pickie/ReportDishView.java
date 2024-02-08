package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.ReportDishController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.view.AppData;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.Map;

public class ReportDishView extends VirtualPaneView {
    private final ReportDishController controller = new ReportDishController();
    private final DishBean dish;
    public ReportDishView(VirtualPaneView parent, DishBean dish) {
        super("/form/pickie/ReportDish.fxml", "REPORTDISH", parent);
        this.dish = dish;
        ToggleGroup toggleGroup = new ToggleGroup();
        radioListedNotUsedIngredient.setToggleGroup(toggleGroup);
        radioNotListedUsedIngredient.setToggleGroup(toggleGroup);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle();
    }


    @FXML
    private Button buttonSave;

    @FXML
    private RadioButton radioListedNotUsedIngredient;

    @FXML
    private RadioButton radioNotListedUsedIngredient;

    @FXML
    private TextField inputIngredient;

    @FXML
    private Text textExplanation;

    @FXML
    private Text textIngredient;

    @FXML
    void clickButtonSave(ActionEvent event) {
        try {
            if(radioListedNotUsedIngredient.isSelected()) {
                controller.addReportUnusedIngredient(
                        AppData.getInstance().getUser(),
                        dish,
                        inputIngredient.getText()
                );
            } else if(radioNotListedUsedIngredient.isSelected()) {
                controller.addReportMissingIngredient(
                        AppData.getInstance().getUser(),
                        dish,
                        inputIngredient.getText()
                );
            } else {
                showError("REPORT_DISH_NO_TYPE");
            }

            showParent();
        } catch (DAOException e) {
            showError(e);
        }
    }

}
