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

public abstract class VirtualPaneView extends VirtualViewGUI {
    @FXML
    protected Text textTitle;
    @FXML
    protected Text textSubtitle;
    @FXML
    private Button buttonBack;
    private final VirtualPaneView parent;
    private static MainController mainController;
    private static MainView mainView;
    public VirtualPaneView(String fxml, VirtualPaneView parent) {
        super(fxml);
        this.parent = parent;
    }
    protected static void init(MainController mainController, MainView mainView) {
        VirtualPaneView.mainController = mainController;
        VirtualPaneView.mainView = mainView;
    }
    protected static BorderPane getMainLayout() {
        return VirtualPaneView.mainView.getMainLayout();
    }

    protected static MainView getMainView() {
        return VirtualPaneView.mainView;
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
        getMainLayout().setCenter(getRoot());
    }
    public void show() {
        if(buttonBack != null) {
            buttonBack.setText(SettingsController.i18n("BACK"));
        }
        setup(null);
        getMainLayout().setCenter(getRoot());
    }

    public void showParent() {
        if(parent != null) {
            parent.setup(null);
            getMainLayout().setCenter(parent.getRoot());
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }
    public void showParent(Map<String, String> arg) {
        if(parent != null) {
            getMainLayout().setCenter(parent.getRoot());
            parent.setup(arg);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }

    @FXML
    private void clickButtonBack(ActionEvent event) {
        showParent();
    }
}
