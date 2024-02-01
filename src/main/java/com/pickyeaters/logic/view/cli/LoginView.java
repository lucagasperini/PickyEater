package com.pickyeaters.logic.view.cli;

import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.view.bean.LoginBean;

import java.util.Map;
import java.util.Scanner;

public class LoginView extends VirtualViewCLI {
    private final LoginController controller;
    public LoginView(LoginController controller) {
        this.controller = controller;
    }

    @Override
    public void show(Map<String, String> arg) {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Email: ");
        String email = userInput.nextLine();
        System.out.print("Password: ");
        String password = userInput.nextLine();
        LoginBean loginBean = new LoginBean(email, password);
        try {
            controller.auth(loginBean);
        } catch (LoginControllerException ex) {
            showError(ex);
            show(arg);
        }
    }

}
