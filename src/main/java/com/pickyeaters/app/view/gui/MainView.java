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
    private final String APP_NAME = "Picky Eater";
    private final int WINDOW_HEIGTH = 720;
    private final int WINDOW_WIDTH = 1280;
    @Override
    public void show() {
        controller.start();
        stage.setTitle(APP_NAME);

        try {
            controller.getInitController().loadFromFile();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            InitView initView = new InitView(controller);
            initView.show();
        }

        LoginView loginView = new LoginView(controller);
        loginView.show();

        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGTH));
        stage.show();
    }
}
