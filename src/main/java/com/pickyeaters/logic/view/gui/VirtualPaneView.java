package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.SettingsController;

import java.util.Map;

public abstract class VirtualPaneView extends VirtualViewGUI {
    private final VirtualPaneView parent;
    private static MainView mainView;
    private static VirtualPaneView activeView;
    protected VirtualPaneView(String fxml, VirtualPaneView parent) {
        super(fxml);
        this.parent = parent;
    }
    protected static void init(MainView mainView) {
        VirtualPaneView.mainView = mainView;
    }
    protected static void setActiveView(VirtualPaneView view) {
        VirtualPaneView.mainView.getMainLayout().setCenter(view.getRoot());
        activeView = view;
    }

    public static VirtualPaneView getActiveView() {
        return activeView;
    }

    protected static MainView getMainView() {
        return VirtualPaneView.mainView;
    }

    protected abstract void setup(Map<String, String> arg);

    public void show(Map<String, String> arg) {
        mainView.hideHeader();
        setActiveView(this);
        setup(arg);
    }
    public void show() {
        show(null);
    }

    public void showParent() {
        showParent(null);
    }
    public void showParent(Map<String, String> arg) {
        if(parent != null) {
            mainView.hideHeader();
            setActiveView(parent);
            parent.setup(arg);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }

    public void showTitle(String key) {
        if(key.isEmpty()) {
            key = "DEFAULT";
        }

        mainView.showTitle(
                SettingsController.i18n(key + "_TITLE"),
                SettingsController.i18n(key + "_SUBTITLE")
        );
    }

    public void hideTitle() {
        mainView.hideTitle();
    }

    public void hideBack() {
        mainView.hideBack();
    }

}
