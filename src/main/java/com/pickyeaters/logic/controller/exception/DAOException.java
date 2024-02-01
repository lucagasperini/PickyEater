package com.pickyeaters.logic.controller.exception;

public class DAOException extends DatabaseControllerException {
    public DAOException(String msg) { super(msg); }
    public DAOException(DatabaseControllerException ex) {
        super("DATABASE_INTERNAL", ex.getMessage());
    }
}
