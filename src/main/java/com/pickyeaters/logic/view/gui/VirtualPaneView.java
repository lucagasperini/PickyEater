package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.Main;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.view.VirtualView;
import com.pickyeaters.logic.controller.application.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public abstract class VirtualPaneView {
    @FXML
    protected Text textTitle;
    @FXML
    protected Text textSubtitle;
    @FXML
    private Button buttonBack;
    private static BorderPane mainLayout;
    private VirtualPaneView parent;
    protected Parent root = null;
    private static MainController mainController;
    public VirtualPaneView(String fxml, VirtualPaneView parent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        loader.setController(this);
        this.parent = parent;
        try {
            this.root = loader.load();
        } catch (IOException ex) {
            System.err.println("[FXML] FATAL ERROR: " + ex.getMessage());
            //TODO:
            System.exit(-1);
        }
    }
    protected static void init(MainController mainController, BorderPane mainLayout) {
        VirtualPaneView.mainController = mainController;
        VirtualPaneView.mainLayout = mainLayout;
    }

    public static MainController getMainController() {
        return mainController;
    }

    protected abstract void setup(Map<String, String> arg);

    public void show(Map<String, String> arg) {
        if(buttonBack != null) {
            buttonBack.setText(SettingsController.i18n("BACK"));
        }
        setup(arg);
        mainLayout.setCenter(this.root);
    }
    public void show() {
        if(buttonBack != null) {
            buttonBack.setText(SettingsController.i18n("BACK"));
        }
        setup(null);
        mainLayout.setCenter(this.root);
    }

    public void showParent() {
        if(parent != null) {
            parent.setup(null);
            mainLayout.setCenter(parent.root);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }
    public void showParent(Map<String, String> arg) {
        if(parent != null) {
            parent.setup(arg);
            mainLayout.setCenter(parent.root);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }

    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }
}
