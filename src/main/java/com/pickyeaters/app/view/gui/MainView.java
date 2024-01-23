package com.pickyeaters.app.view.gui;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainView extends VirtualView {
    public MainView(Stage primaryStage) {
        super(new MainController(), "/form/main.fxml", primaryStage);
    }

    @Override
    public void show() {
        controller.start();
        stage.setTitle("Picky Eater");

        try {
            controller.getInitController().loadFromFile();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            InitView initView = new InitView(controller);
            initView.show();
        }

        LoginView loginView = new LoginView(controller);
        loginView.show();

        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }
}
