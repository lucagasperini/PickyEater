package com.pickyeaters.logic.view.gui.administrator;

import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class MissingIngredientReportView extends VirtualPaneView{
    protected MissingIngredientReportView(VirtualPaneView parent) {
        super("/form/pickie/ManageMissingIngredientReport.fxml", "", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {

    }
}
