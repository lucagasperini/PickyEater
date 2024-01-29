package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class AddIngredientView extends VirtualPaneView {
    @FXML
    private Button buttonBack;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    public AddIngredientView(VirtualPaneView parent) {
        super("/form/restaurateur/AddIngredient.fxml", parent);
    }

    @Override
    protected void setup() {
    }
    @FXML
    private void clickBack(ActionEvent event) {
        showParent();
    }

}
