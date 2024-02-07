package com.pickyeaters.logic.view.gui.administrator;

import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class ReportView extends VirtualPaneView{
    protected ReportView(VirtualPaneView parent) {
        super("/form/pickie/ReportView.fxml", "", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        throw new UnsupportedOperationException();
    }
}
