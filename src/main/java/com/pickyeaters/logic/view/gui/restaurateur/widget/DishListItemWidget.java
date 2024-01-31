package com.pickyeaters.logic.view.gui.restaurateur.widget;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.VirtualWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

public class DishListItemWidget extends VirtualWidget {
    @FXML
    private Text textName;
    @FXML
    private Text textDescription;
    @FXML
    private Button buttonUpdateDish;
    @FXML
    private Button buttonDeleteDish;
    @FXML
    private ListView<String> listviewIngredient;
    @FXML
    private CheckBox checkBoxActive;

    public DishListItemWidget(VirtualPaneView parent, DishBean dishBean) {
        super("/form/restaurateur/widget/MenuTableElement.fxml", parent);

        textName.setText(dishBean.getName());
        textDescription.setText(dishBean.getDescription());

        for(String i : dishBean.getIngredientList()) {
            listviewIngredient.getItems().add(i);
        }
    }


    @FXML
    private void clickCheckBoxActive(ActionEvent actionEvent) {

    }

    @FXML
    private void clickButtonUpdateDish(ActionEvent actionEvent) {

    }

    @FXML
    private void clickButtonDeleteDish(ActionEvent actionEvent) {

    }
}
