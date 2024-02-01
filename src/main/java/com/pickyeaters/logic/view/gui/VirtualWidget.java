package com.pickyeaters.logic.view.gui;

import java.util.Map;

public abstract class VirtualWidget extends VirtualViewGUI {
    private final VirtualPaneView parent;

    protected VirtualWidget(String fxml, VirtualPaneView parent) {
        super(fxml);
        this.parent = parent;
    }

    protected void toParent(Map<String, String> arg) {
        if(parent != null) {
            parent.setup(arg);
        } else {
            throw new RuntimeException("Calling showParent on a root");
        }
    }
}
