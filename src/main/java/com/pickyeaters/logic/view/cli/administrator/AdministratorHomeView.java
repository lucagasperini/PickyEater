package com.pickyeaters.logic.view.cli.administrator;

import com.pickyeaters.logic.controller.application.administrator.AdministratorController;
import com.pickyeaters.logic.view.cli.VirtualRequestView;
import com.pickyeaters.logic.view.cli.pickie.FindRestaurantView;

import java.util.Map;

public class AdministratorHomeView extends VirtualRequestView {
    public AdministratorHomeView() {
        super("Home");
    }

    @Override
    public void show(Map<String, String> arg) {

    }

    @Override
    protected boolean request(String request) {
        switch (request) {
            case "report", "r":
                return true;
            default:
                return false;
        }
    }

    @Override
    protected String requestHelp() {
        return """
                [report, r]""";
    }
}
