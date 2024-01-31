package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.EatingPreferencesController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class EatingPreferencesView extends VirtualPaneView {
    EatingPreferencesController controller;
    public EatingPreferencesView(EatingPreferencesController controller, VirtualPaneView parent) {
        super("/form/pickie/EatingPreferences.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {

    }
}
