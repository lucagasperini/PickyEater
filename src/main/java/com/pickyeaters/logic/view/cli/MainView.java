package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.view.bean.UserBean;
import com.pickyeaters.logic.view.cli.administrator.AdministratorHomeView;
import com.pickyeaters.logic.view.cli.pickie.PickieHomeView;
import com.pickyeaters.logic.view.cli.restaurateur.RestaurateurHomeView;

import java.util.Map;

public class MainView extends VirtualViewCLI {
    private UserBean currentUser;
    public MainView() {
        setMainView(this);
    }

    public UserBean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserBean currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void show(Map<String, String> arg) {
        InitView initView = new InitView();
        initView.show();

        LoginView loginView = new LoginView();
        loginView.show();

        showHomeView();

        System.out.println("Goodbye!");
    }


    private void showHomeView() {
        switch (currentUser.getType()) {
            case PICKIE -> showPickieHomeView();
            case RESTAURATEUR -> showRestaurateurHomeView();
            case ADMIN -> showAdministratorHomeView();
        }
    }

    private void showPickieHomeView() {
        PickieHomeView pickieHomeView = new PickieHomeView();
        pickieHomeView.show();
    }

    private void showRestaurateurHomeView() {
        RestaurateurHomeView restaurateurHomeView = new RestaurateurHomeView();
        restaurateurHomeView.show();
    }

    private void showAdministratorHomeView() {
        AdministratorHomeView administratorHomeView = new AdministratorHomeView();
        administratorHomeView.show();
    }

}
