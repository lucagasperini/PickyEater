package com.pickyeaters.logic.view.gui.administrator;

import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class DishReportView extends VirtualPaneView {
    protected DishReportView(VirtualPaneView parent) {
        super("/form/pickie/ReportDish.fxml", "", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {

    }
}
