package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class AddIngredientView extends VirtualPaneView {
    @FXML
    private Button buttonBack;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private TreeView<String> treeIngredient;
    public AddIngredientView(VirtualPaneView parent) {
        super("/form/restaurateur/AddIngredient.fxml", parent);
    }

    @Override
    protected void setup() {
        try {
            ArrayList<String> arrayList = IngredientDAO.getInstance().getAll().getPaths();
            for(String i : arrayList) {
                System.out.println(i);
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }
    @FXML
    private void clickButtonReport(ActionEvent event) {

    }
    @FXML
    private void clickButtonSave(ActionEvent event) {

    }

    @FXML
    private void clickCheckboxReligiousNeedsHalal(ActionEvent event) {

    }

    @FXML
    private void clickCheckboxReligiousNeedsKosher(ActionEvent event) {

    }
}
