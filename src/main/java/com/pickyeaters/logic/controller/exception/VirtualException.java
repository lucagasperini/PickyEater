package com.pickyeaters.logic.controller.exception;

public abstract class VirtualException extends Exception {
    private final String key;
    public VirtualException(String msg) {
        this("", msg);
    }
    public VirtualException(String key, String msg) {
        super(msg);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
