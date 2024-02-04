package com.pickyeaters.logic.view.gui.administrator;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.administrator.AdministratorController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Map;

public class AdministratorHomeView extends VirtualPaneView {
    @FXML
    private Button buttonManageReport;
    public AdministratorHomeView(VirtualPaneView parent) {
        super("/form/administrator/Home.fxml", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle("ADMINISTRATOR_HOME");

        buttonManageReport.setText(SettingsController.i18n("ADMINISTRATOR_GUI_HOME_VIEW_MANAGE_REPORT"));
    }
}
