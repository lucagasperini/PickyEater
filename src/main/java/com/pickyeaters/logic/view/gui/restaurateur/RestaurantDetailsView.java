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

import java.util.Map;

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
    @FXML
    private Text textOpeningHours;

    @FXML
    private Text textMonday;
    @FXML
    private Text textTuesday;
    @FXML
    private Text textWednesday;
    @FXML
    private Text textThursday;
    @FXML
    private Text textFriday;
    @FXML
    private Text textSaturday;
    @FXML
    private Text textSunday;

    @FXML
    private Text textFrom1;
    @FXML
    private Text textFrom2;
    @FXML
    private Text textFrom3;
    @FXML
    private Text textFrom4;
    @FXML
    private Text textFrom5;
    @FXML
    private Text textFrom6;
    @FXML
    private Text textFrom7;

    @FXML
    private Text textTo1;
    @FXML
    private Text textTo2;
    @FXML
    private Text textTo3;
    @FXML
    private Text textTo4;
    @FXML
    private Text textTo5;
    @FXML
    private Text textTo6;
    @FXML
    private Text textTo7;

    @FXML
    private Text textMyPersonalDetails;
    @FXML
    private Text textMyRestaurantDetails;

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
        setup(null);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        buttonSave.setText(SettingsController.i18n("SAVECHANGES"));
        textTitle.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TITLE"));
        textSubtitle.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_SUBTITLE"));
        textRestaurateurFirstname.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_FIRSTNAME"));
        textRestaurateurLastname.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_LASTNAME"));
        textRestaurateurSsn.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_SSN"));
        textRestaurateurPhone.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_PHONE"));
        textRestaurateurEmail.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_EMAIL"));
        textRestaurantName.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_NAME"));
        textRestaurantAddress.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_ADDRESS"));
        textRestaurantPhone.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_PHONE"));
        textOpeningHours.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_OPENINGHOURS"));

        textMonday.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_MONDAY"));
        textThursday.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TUESDAY"));
        textWednesday.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_WEDNESDAY"));
        textThursday.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_THURSDAY"));
        textFriday.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_FRIDAY"));
        textSaturday.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_SATURDAY"));
        textSunday.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_SUNDAY"));

        textFrom1.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_FROM"));
        textFrom2.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_FROM"));
        textFrom3.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_FROM"));
        textFrom4.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_FROM"));
        textFrom5.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_FROM"));
        textFrom6.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_FROM"));
        textFrom7.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_FROM"));

        textTo1.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TO"));
        textTo2.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TO"));
        textTo3.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TO"));
        textTo4.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TO"));
        textTo5.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TO"));
        textTo6.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TO"));
        textTo7.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_TO"));

        textMyPersonalDetails.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_MYPERSONALDETAILS"));
        textMyRestaurantDetails.setText(SettingsController.i18n("RESTAURATEUR_MANAGERESTAURANTDETAILS_MYRESTAURANTDETAILS"));
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
