package com.pickyeaters.logic.utils;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryProcedure extends VirtualQuery {

    public QueryProcedure(CallableStatement cs, String cmd) {
        super(cs, cmd);
    }

    public void execute() throws DatabaseControllerException {
        try {
            cs.execute();
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot execute: " + ex.getMessage());
        }
    }

    public String getString() throws DatabaseControllerException {
        try {
            return cs.getString(outIndex++);
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot getString: " + ex.getMessage());
        }
    }

    public boolean getBoolean() throws DatabaseControllerException {
        try {
            return cs.getBoolean(outIndex++);
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot getBoolean: " + ex.getMessage());
        }
    }

}
