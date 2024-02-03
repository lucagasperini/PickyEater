package com.pickyeaters.logic.view;

import com.pickyeaters.logic.controller.exception.VirtualException;

public interface ViewInterface {
    void showError(VirtualException ex);
    void showError(String key);
}
