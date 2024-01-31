package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.ReportDishController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class ReportDishView extends VirtualPaneView {
    ReportDishController controller;
    public ReportDishView(ReportDishController controller, VirtualPaneView parent) {
        super("/form/pickie/ReportDish.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {

    }
}
