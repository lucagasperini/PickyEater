package com.pickyeaters.logic.utils;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public abstract class VirtualQuery {
    protected int outIndex = 1;
    protected String cmd;
    protected CallableStatement cs;
    protected int index = 1;

    protected VirtualQuery(CallableStatement cs, String cmd) {
        this.cs = cs;
        this.cmd = cmd;
    }

    public abstract void execute() throws DatabaseControllerException;

    public void close() throws DatabaseControllerException {
        try {
            cs.close();
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot close: " + ex.getMessage());
        }
    }
    public void registerOutBoolean() throws DatabaseControllerException {
        registerOutParameter(Types.BIT);
    }
    public void registerOutString() throws DatabaseControllerException {
        registerOutParameter(Types.VARCHAR);
    }
    private void registerOutParameter(int sqlType) throws DatabaseControllerException {
        try {
            cs.registerOutParameter(index, sqlType);
            cs.setNull(index, sqlType);
            index++;
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot registerOutParameter: " + ex.getMessage());
        }
    }
    public void setString(String val) throws DatabaseControllerException {
        try {
            cs.setString(index++, val);
            outIndex++;
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot setString: " + ex.getMessage());
        }
    }

    public void setBoolean(boolean val) throws DatabaseControllerException {
        try {
            cs.setBoolean(index++, val);
            outIndex++;
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot setBoolean: " + ex.getMessage());
        }
    }

    public abstract String getString() throws DatabaseControllerException;

    public abstract boolean getBoolean() throws DatabaseControllerException;
}
