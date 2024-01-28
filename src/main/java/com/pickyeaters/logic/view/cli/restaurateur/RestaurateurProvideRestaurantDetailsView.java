package com.pickyeaters.logic.view.cli.restaurateur;

import com.pickyeaters.logic.controller.SettingsController;
import com.pickyeaters.logic.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RestaurateurProvideRestaurantDetailsView extends View {
    //texts
    @FXML
    private Text titleText;
    @FXML
    private Text subtitleText;
    @FXML
    private Text myPersonalDetailsText;
    @FXML
    private Text restaurateurNameText;
    @FXML
    private Text restaurateurSurnameText;
    @FXML
    private Text restaurateurBirthDateText;
    @FXML
    private Text restaurateurSsnText;
    @FXML
    private Text restaurateurPhoneNumberText;
    @FXML
    private Text restaurateurEmailAddressText;
    @FXML
    private Text myRestaurantDetailsText;
    @FXML
    private Text restaurantNameText;
    @FXML
    private Text restaurantAddressText;
    @FXML
    private Text restaurantPhoneNumberText;
    @FXML
    private Text openingHoursText;
    @FXML
    private Text mondayText;
    @FXML
    private Text tuesdayText;
    @FXML
    private Text wednesdayText;
    @FXML
    private Text thursdayText;
    @FXML
    private Text fridayText;
    @FXML
    private Text saturdayText;
    @FXML
    private Text sundayText;
    @FXML
    private Text fromText;
    @FXML
    private Text toText;

    //buttons
    @FXML
    private Button goBackButton;
    @FXML
    private Button saveChangesButton;

    //choiceboxes
    @FXML
    private ChoiceBox mondayStartingHour;
    @FXML
    private ChoiceBox mondayClosingHour;
    @FXML
    private ChoiceBox tuesdayStartingHour;
    @FXML
    private ChoiceBox tuesdayClosingHour;
    @FXML
    private ChoiceBox wednesdayStartingHour;
    @FXML
    private ChoiceBox wednesdayClosingHour;
    @FXML
    private ChoiceBox thursdayStartingHour;
    @FXML
    private ChoiceBox thursdayClosingHour;
    @FXML
    private ChoiceBox fridayStartingHour;
    @FXML
    private ChoiceBox fridayClosingHour;
    @FXML
    private ChoiceBox saturdayStartingHour;
    @FXML
    private ChoiceBox saturdayClosingHour;
    @FXML
    private ChoiceBox sundayStartingHour;
    @FXML
    private ChoiceBox sundayClosingHour;

    //textfields
    @FXML
    private TextField restaurateurName;
    @FXML
    private TextField restaurateurSurname;
    @FXML
    private TextField restaurateurBirthdate;
    @FXML
    private TextField restaurateurSsn;
    @FXML
    private TextField restaurateurPhoneNumber;
    @FXML
    private TextField restaurateurEmailAddress;
    @FXML
    private TextField restaurantName;
    @FXML
    private TextField restaurantAddress;
    @FXML
    private TextField restaurantPhoneNumber;

    protected void setup(){
        titleText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_TITLE"));
        subtitleText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_SUBTITLE"));
        myPersonalDetailsText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_MYPERSONALDETAILS"));
        restaurateurNameText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURATEUR_NAME"));
        restaurateurSurnameText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURATEUR_SURNAME"));
        restaurateurBirthDateText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURATEUR_BIRTHDATE"));
        restaurateurSsnText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURATEUR_SSN"));
        restaurateurPhoneNumberText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURATEUR_PHONENUMBER"));
        restaurateurEmailAddressText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURATEUR_EMAILADDRESS"));
        myRestaurantDetailsText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_MYRESTAURANTDETAILS"));
        restaurantNameText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURANT_NAME"));
        restaurantAddressText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURANT_ADDRESS"));
        restaurantPhoneNumberText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_RESTAURANT_PHONENUMBER"));
        openingHoursText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_OPENINGHOURS"));
        mondayText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_MONDAY"));
        tuesdayText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_TUESDAY"));
        wednesdayText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_WEDNESDAY"));
        thursdayText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_THURSDAY"));
        fridayText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_FRIDAY"));
        saturdayText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_SATURDAY"));
        sundayText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_SUNDAY"));
        fromText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_FROM"));
        toText.setText(SettingsController.i18n("RESTAURATEUR_GUI_PROVIDERESTAURANTDETAILS_VIEW_TO"));
        goBackButton.setText(SettingsController.i18n("BACK"));
        saveChangesButton.setText(SettingsController.i18n("SAVECHANGES"));
    }

    public Button getGoBackButton(){
        return goBackButton;
    }

    public Button getSaveChangesButton(){
        return saveChangesButton;
    }

    public Text getTitleText() {
        return titleText;
    }

    public ChoiceBox getMondayStartingHour() {
        return mondayStartingHour;
    }

    public ChoiceBox getMondayClosingHour() {
        return mondayClosingHour;
    }

    public ChoiceBox getTuesdayStartingHour() {
        return tuesdayStartingHour;
    }

    public ChoiceBox getTuesdayClosingHour() {
        return tuesdayClosingHour;
    }

    public ChoiceBox getWednesdayStartingHour() {
        return wednesdayStartingHour;
    }

    public ChoiceBox getWednesdayClosingHour() {
        return wednesdayClosingHour;
    }

    public ChoiceBox getThursdayStartingHour() {
        return thursdayStartingHour;
    }

    public ChoiceBox getThursdayClosingHour() {
        return thursdayClosingHour;
    }

    public ChoiceBox getFridayStartingHour() {
        return fridayStartingHour;
    }

    public ChoiceBox getFridayClosingHour() {
        return fridayClosingHour;
    }

    public ChoiceBox getSaturdayStartingHour() {
        return saturdayStartingHour;
    }

    public ChoiceBox getSaturdayClosingHour() {
        return saturdayClosingHour;
    }

    public ChoiceBox getSundayStartingHour() {
        return sundayStartingHour;
    }

    public ChoiceBox getSundayClosingHour() {
        return sundayClosingHour;
    }

    public TextField getRestaurateurName() {
        return restaurateurName;
    }

    public TextField getRestaurateurSurname() {
        return restaurateurSurname;
    }

    public TextField getRestaurateurBirthdate() {
        return restaurateurBirthdate;
    }

    public TextField getRestaurateurSsn() {
        return restaurateurSsn;
    }

    public TextField getRestaurateurPhoneNumber() {
        return restaurateurPhoneNumber;
    }

    public TextField getRestaurateurEmailAddress() {
        return restaurateurEmailAddress;
    }

    public TextField getRestaurantName() {
        return restaurantName;
    }

    public TextField getRestaurantAddress() {
        return restaurantAddress;
    }

    public TextField getRestaurantPhoneNumber() {
        return restaurantPhoneNumber;
    }
}
