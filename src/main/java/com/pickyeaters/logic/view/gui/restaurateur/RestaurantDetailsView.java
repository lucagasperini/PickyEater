package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.RestaurantDetailsController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.view.bean.RestaurateurBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RestaurantDetailsView extends VirtualPaneView {
    private RestaurantDetailsController controller;
    @FXML
    private TextField inputUserFirstname;
    @FXML
    private TextField inputUserLastname;
    @FXML
    private TextField inputUserSsn;
    @FXML
    private TextField inputUserPhone;
    @FXML
    private TextField inputUserEmail;
    @FXML
    private TextField inputRestaurantName;
    @FXML
    private TextField inputRestaurantAddress;
    @FXML
    private TextField inputRestaurantPhone;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonBack;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private Text textUserFirstname;
    @FXML
    private Text textUserLastname;
    @FXML
    private Text textUserDetails;
    @FXML
    private Text textUserSsn;
    @FXML
    private Text textUserPhone;
    @FXML
    private Text textUserEmail;
    @FXML
    private Text textRestaurantDetails;
    @FXML
    private Text textRestaurantName;
    @FXML
    private Text textRestaurantAddress;
    @FXML
    private Text textRestaurantPhone;

    public RestaurantDetailsView(RestaurantDetailsController controller, VirtualPaneView parent) {
        super("/form/restaurateur/RestaurantDetails.fxml", parent);
        this.controller = controller;
    }

    @FXML
    private void clickSave(ActionEvent event) {

    }

    @FXML
    private void clickBack(ActionEvent event) {
        showParent();
    }

    @Override
    protected void setup() {
        buttonSave.setText(SettingsController.i18n("SAVECHANGES_TEXT"));
        buttonBack.setText(SettingsController.i18n("BACK_TEXT"));
        textTitle.setText(SettingsController.i18n("RESTAURANTDETAILS_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURANTDETAILS_SUBTITLE"));
        textUserFirstname.setText(SettingsController.i18n("FIELD_USER_FIRSTNAME"));
        textUserLastname.setText(SettingsController.i18n("FIELD_USER_LASTNAME"));
        textUserSsn.setText(SettingsController.i18n("FIELD_USER_SSN"));
        textUserPhone.setText(SettingsController.i18n("FIELD_USER_PHONE"));
        textUserEmail.setText(SettingsController.i18n("FIELD_USER_EMAIL"));
        textRestaurantName.setText(SettingsController.i18n("FIELD_RESTAURANT_NAME"));
        textRestaurantAddress.setText(SettingsController.i18n("FIELD_RESTAURANT_ADDRESS"));
        textRestaurantPhone.setText(SettingsController.i18n("FIELD_RESTAURANT_PHONE"));

        try {
            RestaurateurBean restaurateur = controller.get();
            inputUserFirstname.setText(restaurateur.getFirstname());
            inputUserLastname.setText(restaurateur.getLastname());
            inputUserSsn.setText(restaurateur.getSsn());
            inputUserEmail.setText(restaurateur.getEmail());
            inputRestaurantName.setText(restaurateur.getRestaurantName());
            inputRestaurantAddress.setText(restaurateur.getRestaurantAddress());
            inputRestaurantPhone.setText(restaurateur.getRestaurantPhone());
        } catch (LoginControllerException e) {
            //TODO: Add error
            throw new RuntimeException(e);
        }
    }
}
