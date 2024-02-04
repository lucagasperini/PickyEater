package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.MenuDetailsController;
import com.pickyeaters.logic.controller.application.restaurateur.UpdateDishController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.AppData;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.ShowDishBean;
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
    private final MenuDetailsController controller = new MenuDetailsController();
    public MenuDetailsView(VirtualPaneView parent) {
        super("/form/restaurateur/MenuDetails.fxml", parent);
    }
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
        showTitle("RESTAURATEUR_MANAGEMENUDETAILS");
        buttonAddDish.setText(SettingsController.i18n("RESTAURATEUR_MANAGEMENUDETAILS_ADDDISH"));
        textShowinMenu.setText(SettingsController.i18n("RESTAURATEUR_MANAGEMENUDETAILS_SHOWINMENU"));

        setupDishList();
    }

    private void setupDishList() {
        vboxMenu.getChildren().clear();
        try {
            List<ShowDishBean> dishBeanList = controller.getMenu(AppData.getInstance().getRestaurantID());
            for(ShowDishBean i : dishBeanList) {
                vboxMenu.getChildren().add(new DishListItemWidget(this, i).getRoot());
            }
        } catch (ControllerException | BeanException ex) {
            showError(ex);
        }
    }

    private void setupActiveDish(String name) {
        if(name != null) {
            try {
                controller.toggleDish(name, AppData.getInstance().getRestaurantID());
            } catch (ControllerException ex) {
                showError(ex);
            }
        }
    }
    private void setupDeleteDish(String name) {
        if(name != null) {
            try {
                controller.deleteDish(name, AppData.getInstance().getRestaurantID());
            } catch (ControllerException ex) {
                showError(ex);
            }
        }
    }

    private void setupUpdateDish(String name) {
        if(name != null) {
            UpdateDishView view = new UpdateDishView(this, name);
            view.show();
        }
    }
    @FXML
    private void clickButtonAddDish(ActionEvent event) {
        AddDishView view = new AddDishView(this);
        view.show();
    }
}
