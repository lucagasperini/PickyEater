package com.pickyeaters.logic;

import com.pickyeaters.logic.view.cli.MainView;

public class MainCLI {
    void run(String[] args) {
        MainView mainView = new MainView();
        mainView.start();
    }
}