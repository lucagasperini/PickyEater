package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsControllerTest {
    @Test
    void i18n() throws SettingsControllerException {
        SettingsController.getInstance().init();
        assertEquals("OK", SettingsController.i18n("OK"));
    }
}