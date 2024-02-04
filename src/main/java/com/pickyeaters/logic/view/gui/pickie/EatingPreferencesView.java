package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.EatingPreferencesController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class EatingPreferencesView extends VirtualPaneView {
    private final EatingPreferencesController controller = new EatingPreferencesController();
    public EatingPreferencesView(VirtualPaneView parent) {
        super("/form/pickie/EatingPreferences.fxml", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        throw new UnsupportedOperationException();
    }
}
