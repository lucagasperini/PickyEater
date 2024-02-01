package com.pickyeaters.logic.controller.exception;

public class ControllerException extends Exception {
    private final String key;
    public ControllerException(String msg) {
        this("", msg);
    }
    public ControllerException(String key, String msg) {
        super(msg);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
