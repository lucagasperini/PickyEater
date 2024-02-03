package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void init() throws SettingsControllerException {
        SettingsController.getInstance().init();
    }

    @Test
    void testInit() {
    }

    @Test
    void i18n() {
    }
}