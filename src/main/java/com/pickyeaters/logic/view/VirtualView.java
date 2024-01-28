package com.pickyeaters.logic.view;

import com.pickyeaters.logic.controller.MainController;

public abstract class VirtualView {
    protected MainController controller;
    public VirtualView(MainController controller) {
        this.controller = controller;
    }

    public abstract void show();
}
