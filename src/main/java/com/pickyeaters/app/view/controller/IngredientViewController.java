package com.pickyeaters.app.view.controller;

public class IngredientViewController {
    public void request(String[] args) {
        switch(args[1].toLowerCase()) {
            case "add":

                break;
            default:
                throw new UnsupportedOperationException("Cannot execute " + args[1].toLowerCase());
        }
    }
}
