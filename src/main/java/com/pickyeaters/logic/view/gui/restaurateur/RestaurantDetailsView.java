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
    private TextField inputRestaurateurFirstname;
    @FXML
    private TextField inputRestaurateurLastname;
    @FXML
    private TextField inputRestaurateurSsn;
    @FXML
    private TextField inputRestaurateurPhone;
    @FXML
    private TextField inputRestaurateurEmail;
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
    private Text textRestaurateurFirstname;
    @FXML
    private Text textRestaurateurLastname;
    @FXML
    private Text textRestaurateurDetails;
    @FXML
    private Text textRestaurateurSsn;
    @FXML
    private Text textRestaurateurPhone;
    @FXML
    private Text textRestaurateurEmail;
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
    private void clickButtonSave(ActionEvent event) {
        RestaurateurBean restaurateurBean = new RestaurateurBean(
                inputRestaurateurEmail.getText(),
                inputRestaurateurFirstname.getText(),
                inputRestaurateurLastname.getText(),
                inputRestaurateurSsn.getText(),
                inputRestaurantName.getText(),
                inputRestaurantPhone.getText(),
                inputRestaurantAddress.getText()
        );
        controller.set(restaurateurBean);
        setup();
    }

    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }

    @Override
    protected void setup() {
        buttonSave.setText(SettingsController.i18n("SAVECHANGES_TEXT"));
        buttonBack.setText(SettingsController.i18n("BACK_TEXT"));
        textTitle.setText(SettingsController.i18n("RESTAURANTDETAILS_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURANTDETAILS_SUBTITLE"));
        textRestaurateurFirstname.setText(SettingsController.i18n("FIELD_USER_FIRSTNAME"));
        textRestaurateurLastname.setText(SettingsController.i18n("FIELD_USER_LASTNAME"));
        textRestaurateurSsn.setText(SettingsController.i18n("FIELD_USER_SSN"));
        textRestaurateurPhone.setText(SettingsController.i18n("FIELD_USER_PHONE"));
        textRestaurateurEmail.setText(SettingsController.i18n("FIELD_USER_EMAIL"));
        textRestaurantName.setText(SettingsController.i18n("FIELD_RESTAURANT_NAME"));
        textRestaurantAddress.setText(SettingsController.i18n("FIELD_RESTAURANT_ADDRESS"));
        textRestaurantPhone.setText(SettingsController.i18n("FIELD_RESTAURANT_PHONE"));

        try {
            RestaurateurBean restaurateur = controller.get();
            inputRestaurateurFirstname.setText(restaurateur.getFirstname());
            inputRestaurateurLastname.setText(restaurateur.getLastname());
            inputRestaurateurSsn.setText(restaurateur.getSsn());
            inputRestaurateurEmail.setText(restaurateur.getEmail());
            inputRestaurantName.setText(restaurateur.getRestaurantName());
            inputRestaurantAddress.setText(restaurateur.getRestaurantAddress());
            inputRestaurantPhone.setText(restaurateur.getRestaurantPhone());
        } catch (LoginControllerException e) {
            //TODO: Add error
            throw new RuntimeException(e);
        }
    }
}
