package com.pickyeaters.logic.view;

import com.pickyeaters.logic.controller.exception.ControllerException;

public interface ViewInterface {
    void showError(ControllerException ex);
    void showError(String key);
}
