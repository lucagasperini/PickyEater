package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.view.cli.administrator.AdministratorHomeView;
import com.pickyeaters.logic.view.cli.pickie.PickieHomeView;
import com.pickyeaters.logic.view.cli.restaurateur.RestaurateurHomeView;

import java.util.Map;

public class MainView extends VirtualViewCLI {

    private final MainController controller = new MainController();
    public MainView() {
        init(controller);
    }
    @Override
    public void show(Map<String, String> arg) {
        controller.start();
        InitView initView = new InitView(controller.getInit());
        initView.show();

        LoginView loginView = new LoginView(controller.getLogin());
        loginView.show();

        showHomeView();

        System.out.println("Goodbye!");
    }


    private void showHomeView() {
        try {
            switch (controller.getLogin().getUserType()) {
                case PICKIE -> showPickieHomeView();
                case RESTAURATEUR -> showRestaurateurHomeView();
                case ADMIN -> showAdministratorHomeView();
            }
        } catch (LoginControllerException ex) {
            showError(ex);
        }
    }

    private void showPickieHomeView() {
        PickieHomeView pickieHomeView = new PickieHomeView(
                controller.getPickie()
        );
        pickieHomeView.show();
    }

    private void showRestaurateurHomeView() {
        RestaurateurHomeView restaurateurHomeView = new RestaurateurHomeView(
                controller.getRestaurateur()
        );
        restaurateurHomeView.show();
    }

    private void showAdministratorHomeView() {
        AdministratorHomeView administratorHomeView = new AdministratorHomeView(
                controller.getAdministrator()
        );
        administratorHomeView.show();
    }

}
