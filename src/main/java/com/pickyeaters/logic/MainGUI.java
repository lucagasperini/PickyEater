package com.pickyeaters.logic;

import com.pickyeaters.logic.view.gui.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {
    void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainView mainView = new MainView(stage);
        mainView.show();
    }
}