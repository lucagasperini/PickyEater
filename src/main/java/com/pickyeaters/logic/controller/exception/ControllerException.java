package com.pickyeaters.logic.controller.exception;

public class ControllerException extends VirtualException {
    public ControllerException(String msg) {
        super(msg);
    }
    public ControllerException(String key, String msg) {
        super(key, msg);
    }
}
