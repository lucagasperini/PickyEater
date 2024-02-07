package com.pickyeaters.logic.view.cli.pickie;

import com.pickyeaters.logic.view.cli.VirtualRequestView;

import java.util.Map;

public class PickieHomeView extends VirtualRequestView {
    public PickieHomeView() {
        super("Home", "");
    }

    @Override
    public void show(Map<String, String> arg) {

    }

    @Override
    protected boolean request(String request) {
        switch (request) {
            case "find", "f":
                FindRestaurantView findRestaurantView = new FindRestaurantView();
                findRestaurantView.show();
                return true;
            case "pref", "p":
                return true;
            case "review", "r":
                return true;
            default:
                return false;
        }
    }

    @Override
    protected String requestHelp() {
        return """
                [find, f]
                [pref, p]
                [review, r]""";
    }
}
