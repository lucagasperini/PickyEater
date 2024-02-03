package com.pickyeaters.logic.controller.exception;

public class BeanException extends VirtualException {
    public BeanException(String msg) {
        super(msg);
    }

    public BeanException(String key, String msg) {
        super(key, msg);
    }
}
