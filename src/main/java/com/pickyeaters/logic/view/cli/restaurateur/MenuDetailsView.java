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
        switch (request) {
            case "show", "s":
                showMenu();
                return true;
            case "edit", "e":
                editDish();
                return true;
            case "add", "a":
                addDish();
                return true;
            case "remove", "r":
                removeDish();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected String requestHelp() {
        return """
                [show, s]
                [edit, e]
                [add, a]
                [remove, r]""";
    }

    private void showMenu() {
        throw new UnsupportedOperationException();
    }

    private void editDish() {
        throw new UnsupportedOperationException();
    }

    private void addDish() {
        throw new UnsupportedOperationException();
    }

    private void removeDish() {
        throw new UnsupportedOperationException();
    }
}
