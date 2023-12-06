package com.pickyeaters.app.controller;

public class SystemController {
    public static void load() {
        loadConfig();
        loadDatabase();
    }

    private static void loadDatabase() {
        // TODO: Pass connection parameters here!
        SqlController.connect();
    }
    private static void loadConfig() {
        // TODO: Do something!
    }
}
