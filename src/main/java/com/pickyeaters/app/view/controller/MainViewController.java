package com.pickyeaters.app.view.controller;

import com.pickyeaters.app.view.cli.HelpView;
import com.pickyeaters.app.view.cli.IngredientView;
import com.pickyeaters.app.view.cli.InitView;

public class MainViewController {
    private boolean isRunning = false;
    public void start() {
        isRunning = true;
        InitView initView = new InitView();
        initView.show();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void request(String request) {
        String[] tmp = request.split(" ");
        switch (tmp[0].toLowerCase()) {
            case "help":
            case "h":
                HelpView helpView = new HelpView();
                helpView.show();
                break;
            case "ingredient":
            case "i":
                IngredientView ingredientView = new IngredientView();
                ingredientView.show(tmp);
                break;
            case "quit":
            case "q":
                quit();
                break;
            default:
                throw new UnsupportedOperationException("Cannot execute " + tmp[0].toLowerCase());
        }
    }

    public void quit() {
        isRunning = false;
    }
}
