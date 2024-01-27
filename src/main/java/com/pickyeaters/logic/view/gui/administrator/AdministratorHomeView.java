package com.pickyeaters.logic.view.gui.administrator;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.application.administrator.AdministratorController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class AdministratorHomeView extends VirtualPaneView {
    private AdministratorController controller;
    @FXML
    private Text textTitle;
    @FXML
    private Text textSubtitle;
    @FXML
    private Button buttonManageReport;
    public AdministratorHomeView(AdministratorController controller, VirtualPaneView parent) {
        super("/form/administrator/homeView.fxml", parent);
        this.controller = controller;
    }

    @Override
    protected void setup() {
        textTitle.setText(SettingsController.i18n("ADMINISTRATOR_GUI_HOME_VIEW_TITLE"));
        textSubtitle.setText(SettingsController.i18n("ADMINISTRATOR_GUI_HOME_VIEW_SUBTITLE"));
        buttonManageReport.setText(SettingsController.i18n("ADMINISTRATOR_GUI_HOME_VIEW_MANAGE_REPORT"));
    }
}
