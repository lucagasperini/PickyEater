package com.pickyeaters.logic.utils;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryResultSet extends VirtualQuery{
    private ResultSet rs;
    public QueryResultSet(CallableStatement cs, String cmd) {
        super(cs, cmd);
    }

    public void execute() throws DatabaseControllerException {
        try {
            cs.execute();
            rs = cs.getResultSet();
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot execute: " + ex.getMessage());
        }
    }

    public boolean next() throws DatabaseControllerException {
        if(rs == null) {
            throw new DatabaseControllerException("Result set is null");
        }
        try {
            outIndex = 1;
            return rs.next();
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot next: " + ex.getMessage());
        }
    }

    public String getString() throws DatabaseControllerException {
        try {

            if(rs == null)
                throw new DatabaseControllerException("Cannot getString from Result set");
            return rs.getString(outIndex++);
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot getString: " + ex.getMessage());
        }
    }

    public boolean getBoolean() throws DatabaseControllerException {
        try {
            if(rs == null)
                throw new DatabaseControllerException("Cannot getBoolean from Result set");
            return rs.getBoolean(outIndex++);
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot getBoolean: " + ex.getMessage());
        }
    }
}
