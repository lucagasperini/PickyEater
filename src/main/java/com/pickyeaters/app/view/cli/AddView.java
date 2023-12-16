package com.pickyeaters.app.view.cli;

import com.pickyeaters.app.model.Session;

public class AddView implements ViewCLI {

    public AddView(Session session) {

    }
    @Override
    public void show(String[] args) {
        switch(args[1].toLowerCase()) {
            case "ingredient":
            case "i":

                break;
            default:
                //throw new UnsupportedOperationException("Cannot execute " + args[1].toLowerCase());
        }
    }
}
