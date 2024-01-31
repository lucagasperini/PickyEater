package com.pickyeaters.logic.view;

import com.pickyeaters.logic.controller.application.MainController;

public abstract class VirtualView {
    protected MainController controller;
    protected VirtualView(MainController controller) {
        this.controller = controller;
    }

    public abstract void show();
}
