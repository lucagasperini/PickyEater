package com.pickyeaters.logic.controller.exception;

public class LoginControllerException extends ControllerException {
    public LoginControllerException(String msg) { super(msg); }

    public LoginControllerException(String key, String msg) { super(key, msg); }
}
