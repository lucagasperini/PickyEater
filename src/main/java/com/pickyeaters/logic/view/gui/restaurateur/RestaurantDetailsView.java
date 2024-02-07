package com.pickyeaters.logic.view.gui.restaurateur;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.restaurateur.RestaurantDetailsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.view.AppData;
import com.pickyeaters.logic.view.bean.CityBean;
import com.pickyeaters.logic.view.bean.RestaurateurBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Map;

public class RestaurantDetailsView extends VirtualPaneView {
    private final RestaurantDetailsController controller = new RestaurantDetailsController();

    public RestaurantDetailsView(VirtualPaneView parent) {
        super("/form/restaurateur/RestaurantDetails.fxml", "RESTAURATEUR_MANAGERESTAURANTDETAILS", parent);

        buttonSave.setText(i18nGlobal("SAVECHANGES"));
        textRestaurateurFirstname.setText(i18n("RESTAURATEUR_FIRSTNAME"));
        textRestaurateurLastname.setText(i18n("RESTAURATEUR_LASTNAME"));
        textRestaurateurSsn.setText(i18n("RESTAURATEUR_SSN"));
        textRestaurateurPhone.setText(i18n("RESTAURATEUR_PHONE"));
        textRestaurateurEmail.setText(i18n("RESTAURATEUR_EMAIL"));
        textRestaurantName.setText(i18n("RESTAURANT_NAME"));
        textRestaurantAddress.setText(i18n("RESTAURANT_ADDRESS"));
        textRestaurantPhone.setText(i18n("RESTAURANT_PHONE"));
        textOpeningHours.setText(i18n("OPENINGHOURS"));

        textMonday.setText(i18n("MONDAY"));
        textThursday.setText(i18n("TUESDAY"));
        textWednesday.setText(i18n("WEDNESDAY"));
        textThursday.setText(i18n("THURSDAY"));
        textFriday.setText(i18n("FRIDAY"));
        textSaturday.setText(i18n("SATURDAY"));
        textSunday.setText(i18n("SUNDAY"));

        String from = i18n("FROM");

        textFrom1.setText(from);
        textFrom2.setText(from);
        textFrom3.setText(from);
        textFrom4.setText(from);
        textFrom5.setText(from);
        textFrom6.setText(from);
        textFrom7.setText(from);

        String to = i18n("TO");

        textTo1.setText(to);
        textTo2.setText(to);
        textTo3.setText(to);
        textTo4.setText(to);
        textTo5.setText(to);
        textTo6.setText(to);
        textTo7.setText(to);

        textMyPersonalDetails.setText(i18n("MYPERSONALDETAILS"));
        textMyRestaurantDetails.setText(i18n("MYRESTAURANTDETAILS"));
        try {
            RestaurateurBean restaurateur = controller.get(AppData.getInstance().getUser());
            inputRestaurateurFirstname.setText(restaurateur.getFirstname());
            inputRestaurateurLastname.setText(restaurateur.getLastname());
            inputRestaurateurPhone.setText(restaurateur.getPhone());
            inputRestaurateurSsn.setText(restaurateur.getSsn());
            inputRestaurateurEmail.setText(restaurateur.getEmail());
            inputRestaurantName.setText(restaurateur.getRestaurantName());
            inputRestaurantAddress.setText(restaurateur.getRestaurantAddress());
            inputRestaurantPhone.setText(restaurateur.getRestaurantPhone());
            inputRestaurantCity.setText(restaurateur.getRestaurantCity().getName());
        } catch (DAOException ex) {
            //TODO: Add error
            showError(ex);
        }

    }

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
    private TextField inputRestaurantCity;
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
    private Text textRestaurantCity;
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
    @FXML
    private void clickButtonSave(ActionEvent event) {
        RestaurateurBean restaurateurBean = new RestaurateurBean(
                inputRestaurateurEmail.getText(),
                inputRestaurateurFirstname.getText(),
                inputRestaurateurLastname.getText(),
                inputRestaurateurPhone.getText(),
                inputRestaurateurSsn.getText(),
                inputRestaurantName.getText(),
                inputRestaurantPhone.getText(),
                inputRestaurantAddress.getText(),
                new CityBean(inputRestaurantCity.getText())
        );
        try {
            controller.set(
                    restaurateurBean,
                    AppData.getInstance().getUser()
            );
            showParent();
        } catch (ControllerException ex) {
            showError(ex);
        }
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle();
    }
}
