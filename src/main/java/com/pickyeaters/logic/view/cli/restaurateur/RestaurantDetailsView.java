package com.pickyeaters.logic.view.cli.restaurateur;

import com.pickyeaters.logic.controller.application.restaurateur.RestaurantDetailsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.view.AppData;
import com.pickyeaters.logic.view.bean.RestaurateurBean;
import com.pickyeaters.logic.view.cli.VirtualRequestView;

import java.util.Map;

public class RestaurantDetailsView extends VirtualRequestView {
    private final RestaurantDetailsController controller = new RestaurantDetailsController();
    public RestaurantDetailsView() {
        super("RestaurantDetails", "RESTAURATEUR_MANAGERESTAURANTDETAILS");
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
            RestaurateurBean bean = controller.get(AppData.getInstance().getUserEmail());
            printField("RESTAURATEUR_EMAIL", bean.getEmail());
            printField("RESTAURATEUR_FIRSTNAME", bean.getFirstname());
            printField("RESTAURATEUR_LASTNAME", bean.getLastname());
            printField("RESTAURATEUR_SSN", bean.getSsn());
            printField("RESTAURANT_NAME", bean.getRestaurantName());
            printField("RESTAURANT_ADDRESS", bean.getRestaurantAddress());
            printField("RESTAURANT_PHONE", bean.getRestaurantPhone());
        } catch (DAOException e) {
            showError(e);
        }
    }

    private void editDetails() {
        try {
            RestaurateurBean bean = controller.get(AppData.getInstance().getUserEmail());
            bean.setEmail(
                    askField("RESTAURATEUR_EMAIL", bean.getEmail())
            );
            bean.setFirstname(
                    askField("RESTAURATEUR_FIRSTNAME", bean.getFirstname())
            );
            bean.setLastname(
                    askField("RESTAURATEUR_LASTNAME", bean.getLastname())
            );
            bean.setSsn(
                    askField("RESTAURATEUR_SSN", bean.getSsn())
            );
            bean.setRestaurantName(
                    askField("RESTAURANT_NAME", bean.getRestaurantName())
            );
            bean.setRestaurantAddress(
                    askField("RESTAURANT_ADDRESS", bean.getRestaurantAddress())
            );
            bean.setRestaurantPhone(
                    askField("RESTAURANT_PHONE", bean.getRestaurantPhone())
            );

            controller.set(
                    bean,
                    AppData.getInstance().getUserID(),
                    AppData.getInstance().getRestaurantID()
            );
        } catch (ControllerException e) {
            showError(e);
        }
    }
}
