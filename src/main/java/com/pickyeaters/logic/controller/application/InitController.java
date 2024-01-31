package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.SettingsBean;

public class InitController extends VirtualController {
    public InitController(MainController main) {
        super(main);
    }
    public void loadFromFile() throws SettingsControllerException, DatabaseControllerException {
        // Try to load config from file system
        SettingsController.getInstance().init();
        // try to init database connection
        DatabaseController.getInstance().init();
        // try to save current config on file
        SettingsController.getInstance().persist();
    }

    public void loadFromInput(SettingsBean settingsBean) throws SettingsControllerException, DatabaseControllerException {
        // Try to load config from settingsBean
        SettingsController.getInstance().init(settingsBean);
        // try to init database connection
        DatabaseController.getInstance().init();
        // try to save current config on file
        SettingsController.getInstance().persist();
    }


}
