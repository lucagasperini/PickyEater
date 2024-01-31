package com.pickyeaters.logic.controller.exception;

public class DAOException extends DatabaseControllerException {
    public DAOException(String msg) { super(msg); }
    public DAOException(DatabaseControllerException ex) {
        super(ex.getMessage());
        setKey("DATABASE_INTERNAL");
    }
}
