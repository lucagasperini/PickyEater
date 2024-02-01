package com.pickyeaters.logic.view.cli.administrator;

import com.pickyeaters.logic.controller.application.administrator.AdministratorController;
import com.pickyeaters.logic.view.cli.VirtualRequestView;

import java.util.Map;

public class AdministratorHomeView extends VirtualRequestView {
    private final AdministratorController controller;
    public AdministratorHomeView(AdministratorController controller) {
        super("Home");
        this.controller = controller;
    }

    @Override
    public void show(Map<String, String> arg) {

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
