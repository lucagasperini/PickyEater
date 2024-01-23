package com.pickyeaters.app.view.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public void init(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView(primaryStage);
        mainView.show();
    }
}
