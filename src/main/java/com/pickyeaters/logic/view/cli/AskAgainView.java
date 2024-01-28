package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.SettingsController;
import com.pickyeaters.logic.view.View;

import java.util.Scanner;

public class AskAgainView extends View {
    private String questionText;
    private String yesText;
    private String noText;
    private String type;
    private Scanner scanner;

    public AskAgainView(String operation){
        if (operation == "unsuccessful save"){
            type = "no db connection error";
        }
        setup();
    }

    public AskAgainView(String operation, String object){
        if (operation == "remove" && object == "dish"){
            type = "remove dish";
        }
        else if (operation == "remove" && object == "ingredient"){
            type = "remove ingredient";
        }
        setup();
    }

    protected void setup(){
        switch (type){
            case "unsuccessful save":
                titleText = (SettingsController.i18n("NO_DB_CONNECTION_ERROR_TITLE"));
                messageText = (SettingsController.i18n("NO_DB_CONNECTION_ERROR_MESSAGE"));
                questionText = (SettingsController.i18n("NO_DB_CONNECTION_ERROR_QUESTION"));
                break;
            case "remove dish":
                titleText = (SettingsController.i18n("REMOVEDISH_VIEW_TITLE"));
                messageText = (SettingsController.i18n("REMOVEDISH_VIEW_MESSAGE"));
                questionText = (SettingsController.i18n("RREMOVEDISH_VIEW_QUESTION"));
                break;
            case "remove ingredient":
                titleText = (SettingsController.i18n("REMOVEINGREDIENT_VIEW_TITLE"));
                messageText = (SettingsController.i18n("REMOVEINGREDIENT_VIEW_MESSAGE"));
                questionText = (SettingsController.i18n("REMOVEINGREDIENT_VIEW_QUESTION"));
                break;

        }
        yesText = (SettingsController.i18n("ASKAGAIN_VIEW_YES"));
        noText = (SettingsController.i18n("ASKAGAIN_VIEW_NO"));
    }
    public void show(){
        showTitle();
        showMessage();
        showQuestion();
        showYesAnswer();
        showNoAnswer();

    }


    public void showTitle(){
        System.out.printf("%s%n", titleText);
    }
    public void showMessage(){
        System.out.printf("%s%n", messageText);
    }
    public void showQuestion(){
        System.out.printf("%s%n", questionText);
    }
    public void showYesAnswer(){
        System.out.printf("1) %s%n", yesText);
    }
    public void showNoAnswer(){
        System.out.printf("2) %s", noText);
    }
    public Boolean getAnswer(){
        String response = scanner.next().toLowerCase();
        return response.equals("1");
    }
}
