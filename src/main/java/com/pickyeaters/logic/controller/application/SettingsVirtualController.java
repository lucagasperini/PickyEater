package com.pickyeaters.logic.controller.application;

import com.pickyeaters.app.view.bean.SettingsBean;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;

import java.util.Properties;

public interface SettingsVirtualController {
    void load(Properties prop) throws SettingsControllerException;
    void load(SettingsBean settingsBean) throws SettingsControllerException;
    void validate() throws SettingsControllerException;
    void save(Properties prop) throws SettingsControllerException;
}
