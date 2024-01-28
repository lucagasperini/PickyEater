package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.SettingsController;
import com.pickyeaters.logic.view.View;

public class OperationFeedbackView extends View {
    private String messageText;
    private String okText;
    private String type;

    public OperationFeedbackView(String operation, View nextView){
        if (operation == "successful save"){
            type = "no db connection error";
        }
        setup();
    }

    protected void setup(){
        switch (type){
            case "incorrect save":
                messageText = (SettingsController.i18n("NO_DB_CONNECTION_ERROR_MESSAGE"));
                break;
        }
        okText = (SettingsController.i18n("OK"));
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
