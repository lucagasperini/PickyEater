package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.MenuDetailsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.restaurateur.widget.DishListItemWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;

public class MenuDetailsView extends VirtualPaneView {
    MenuDetailsController controller;
    public MenuDetailsView(MenuDetailsController controller, VirtualPaneView parent) {
        super("/form/restaurateur/MenuDetails.fxml", parent);
        this.controller = controller;
    }

    @FXML
    private Button buttonBack;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private VBox vboxMenu;
    @FXML
    private Button buttonAddDish;
    @FXML
    private Text textShowinMenu;

    @Override
    protected void setup(Map<String, String> arg) {
        if(arg != null) {
            setupDeleteDish(arg.get("deleteDish"));
            setupUpdateDish(arg.get("updateDish"));
            setupActiveDish(arg.get("activeDish"));
        }
        textTitle.setText(SettingsController.i18n("RESTAURATEUR_MANAGEMENUDETAILS_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURATEUR_MANAGEMENUDETAILS_SUBTITLE"));
        buttonAddDish.setText(SettingsController.i18n("RESTAURATEUR_MANAGEMENUDETAILS_ADDDISH"));
        buttonBack.setText(SettingsController.i18n("BACK"));
        textShowinMenu.setText(SettingsController.i18n("RESTAURATEUR_MANAGEMENUDETAILS_SHOWINMENU"));

        setupDishList();
    }

    private void setupDishList() {
        vboxMenu.getChildren().clear();
        try {
            List<DishBean> dishBeanList = controller.getMenu();
            for(DishBean i : dishBeanList) {
                vboxMenu.getChildren().add(new DishListItemWidget(this, i).getRoot());
            }
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupActiveDish(String dishID) {
        if(dishID != null) {
            try {
                controller.toggleDish(dishID);
            } catch (ControllerException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void setupDeleteDish(String dishID) {
        if(dishID != null) {
            try {
                controller.deleteDish(dishID);
            } catch (ControllerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setupUpdateDish(String dishID) {
        if(dishID != null) {
            AddDishView view = new AddDishView(controller.getAddDish(), this);
            view.show();
        }
    }
    @FXML
    private void clickButtonAddDish(ActionEvent event) {
        AddDishView view = new AddDishView(controller.getAddDish(), this);
        view.show();
    }

    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }

}
