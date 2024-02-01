package com.pickyeaters.logic.view.cli.pickie;

import com.pickyeaters.logic.controller.application.pickie.PickieController;
import com.pickyeaters.logic.view.cli.VirtualRequestView;
import com.pickyeaters.logic.view.cli.VirtualViewCLI;

import java.util.Map;

public class PickieHomeView extends VirtualRequestView {
    private PickieController controller;
    public PickieHomeView(PickieController controller) {
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
