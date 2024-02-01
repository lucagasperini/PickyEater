package com.pickyeaters.logic.view.cli.restaurateur;

import com.pickyeaters.logic.controller.application.restaurateur.MenuDetailsController;
import com.pickyeaters.logic.view.cli.VirtualRequestView;
import com.pickyeaters.logic.view.cli.VirtualViewCLI;

import java.util.Map;

public class MenuDetailsView extends VirtualRequestView {
    private MenuDetailsController controller;
    public MenuDetailsView(MenuDetailsController controller) {
        super("MenuDetails");
        this.controller = controller;
    }

    @Override
    public void show(Map<String, String> arg) {
        requestLoop();
    }

    @Override
    protected boolean request(String request) {
        return false;
    }

    @Override
    protected String requestHelp() {
        throw new UnsupportedOperationException();
    }
}
