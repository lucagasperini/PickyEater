package com.pickyeaters.logic.view.cli.restaurateur;

import com.pickyeaters.logic.controller.application.restaurateur.RestaurantDetailsController;
import com.pickyeaters.logic.controller.application.restaurateur.RestaurateurController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.view.bean.RestaurateurBean;
import com.pickyeaters.logic.view.cli.VirtualRequestView;
import com.pickyeaters.logic.view.cli.VirtualViewCLI;

import java.util.Map;

public class RestaurantDetailsView extends VirtualRequestView {
    private RestaurantDetailsController controller;
    public RestaurantDetailsView(RestaurantDetailsController controller) {
        super("RestaurantDetails");
        this.controller = controller;
    }

    @Override
    public void show(Map<String, String> arg) {
        requestLoop();
    }

    @Override
    protected boolean request(String request) {
        switch (request) {
            case "show", "s":
                showDetails();
                return true;
            case "edit", "e":
                editDetails();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected String requestHelp() {
        return """
                [show, s]
                [edit, e]""";
    }

    private void showDetails() {
        try {
            RestaurateurBean bean = controller.get();
            printField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_EMAIL", bean.getEmail());
            printField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_FIRSTNAME", bean.getFirstname());
            printField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_LASTNAME", bean.getLastname());
            printField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_SSN", bean.getSsn());
            printField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_NAME", bean.getRestaurantName());
            printField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_ADDRESS", bean.getRestaurantAddress());
            printField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_PHONE", bean.getRestaurantPhone());
        } catch (LoginControllerException e) {
            showError(e);
        }
    }

    private void editDetails() {
        try {
            RestaurateurBean bean = controller.get();
            bean.setEmail(
                    askField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_EMAIL", bean.getEmail())
            );
            bean.setFirstname(
                    askField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_FIRSTNAME", bean.getFirstname())
            );
            bean.setLastname(
                    askField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_LASTNAME", bean.getLastname())
            );
            bean.setSsn(
                    askField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURATEUR_SSN", bean.getSsn())
            );
            bean.setRestaurantName(
                    askField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_NAME", bean.getRestaurantName())
            );
            bean.setRestaurantAddress(
                    askField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_ADDRESS", bean.getRestaurantAddress())
            );
            bean.setRestaurantPhone(
                    askField("RESTAURATEUR_MANAGERESTAURANTDETAILS_RESTAURANT_PHONE", bean.getRestaurantPhone())
            );

            controller.set(bean);
        } catch (ControllerException e) {
            showError(e);
        }
    }
}
