package com.pickyeaters.logic.controller.exception;

public class DatabaseControllerException extends ControllerException {
    public DatabaseControllerException(String msg) { super(msg); }
    public DatabaseControllerException(String key, String msg) { super(key, msg); }
}
