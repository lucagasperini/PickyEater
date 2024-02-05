package com.pickyeaters.logic.view.gui.widget;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.VirtualWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class IngredientListItemWidget extends VirtualWidget {

    @FXML
    private Button buttonDeleteIngredient;

    @FXML
    private Text textIngredientCooked;

    @FXML
    private Text textIngredientName;

    @FXML
    private Text textIngredientOptional;

    @FXML
    private Text textSeparator1;

    @FXML
    private Text textSeparator2;

    private DishIngredientBean bean;
    public IngredientListItemWidget(VirtualPaneView parent, DishIngredientBean bean) {
        super("/form/widget/IngredientListItemWidget.fxml", parent);
        this.bean = bean;
        textIngredientName.setText(bean.getName());
        textIngredientCooked.setText(
                bean.isCooked() ?
                        SettingsController.i18n("PICKY_ADDDISLIKEDINGREDIENT_COOKINGMETHOD_COOKED") :
                        SettingsController.i18n("PICKY_ADDDISLIKEDINGREDIENT_COOKINGMETHOD_RAW")
        );
        if(bean.isOptional()) {
            textIngredientOptional.setText(
                            SettingsController.i18n("PICKY_ADDDISLIKEDINGREDIENT_OPTIONAL")
            );
        } else {
            textSeparator2.setVisible(false);
            textIngredientOptional.setVisible(false);
        }
        buttonDeleteIngredient.setText(SettingsController.i18n("RESTAURATEUR_ADDDISH_REMOVEINGREDIENT"));
    }

    @FXML
    private void clickButtonDeleteIngredient(ActionEvent actionEvent) {
        Map<String, String> arg = new HashMap<>();
        arg.put("removeIngredient", textIngredientName.getText());
        toParent(arg);
    }

    public DishIngredientBean getBean() {
        return bean;
    }
}