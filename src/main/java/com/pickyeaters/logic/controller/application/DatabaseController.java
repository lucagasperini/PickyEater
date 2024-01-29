package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.model.Pickie;
import com.pickyeaters.logic.model.Session;
import com.pickyeaters.logic.model.SettingsDatabase;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.User;

import java.sql.*;
import java.util.Vector;

public class DatabaseController {
    private static DatabaseController instance = new DatabaseController();

    public static DatabaseController getInstance() {
        return instance;
    }

    private DatabaseController() {

    }

    private Connection conn = null;
    public void init() throws DatabaseControllerException {
        // validation of settings are performed on SettingsDatabaseController
        SettingsDatabase settings = SettingsController.getInstance().getSettings().getDatabase();
        String url = formatURL(
                settings.getDriver(),
                settings.getHost(),
                settings.getPort(),
                settings.getName());

        connect(url, settings.getUser(), settings.getPassword());
    }
    private String formatURL(String driver, String host, int port, String name) {
        return "jdbc:" + driver + "://" + host + ":" + port + "/" + name;
    }
    private void connect(String url, String user, String password) throws DatabaseControllerException {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot connect to database: " + url);
        }
    }

    public boolean login(String username, String password) throws DatabaseControllerException {
        if(conn == null) {
            throw new DatabaseControllerException("Connection is not ready.");
        }
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("CALL login(?,?,?)");
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.setNull(3, Types.VARCHAR);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot create a login call.");
        }

        try {
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot execute a login call.");
        }

        try {
            String token = cs.getString(3);
            return token != null;
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot get response from login call.");
        }

    }

    public class Query {
        int outIndex = 1;
        String cmd;
        CallableStatement cs;
        ResultSet rs;
        boolean isResultSet = false;
        int index = 1;
        Query(CallableStatement _cs, String _cmd, boolean isResultSet) {
            cs = _cs;
            cmd = _cmd;
            this.isResultSet = isResultSet;
        }

        public void execute() throws DatabaseControllerException {
            try {
                cs.execute();
                if(isResultSet)
                    rs = cs.getResultSet();
            } catch (SQLException ex) {
                throw new DatabaseControllerException("Cannot execute: " + ex.getMessage());
            }
        }

        public boolean next() throws DatabaseControllerException {
            if(!isResultSet) {
                throw new DatabaseControllerException("Not a query Result set");
            }
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

        public void close() throws DatabaseControllerException {
            try {
                cs.close();
            } catch (SQLException ex) {
                throw new DatabaseControllerException("Cannot close: " + ex.getMessage());
            }
        }

        public void registerOutParameter(int sqlType) throws DatabaseControllerException {
            try {
                cs.registerOutParameter(index, sqlType);
                cs.setNull(index, Types.VARCHAR);
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

        public String getString() throws DatabaseControllerException {
            try {
                if(!isResultSet)
                    return cs.getString(outIndex++);
                else {
                    if(rs == null)
                        throw new DatabaseControllerException("Cannot getString from Result set");
                    return rs.getString(outIndex++);
                }
            } catch (SQLException ex) {
                throw new DatabaseControllerException("Cannot getString: " + ex.getMessage());
            }
        }

    }

    public Query query(String query) throws DatabaseControllerException {
        if(conn == null) {
            throw new DatabaseControllerException("Connection is not ready");
        }

        try {
            return new Query(conn.prepareCall(query), query, false);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot prepare query");
        }
    }

    public Query queryResultSet(String query) throws DatabaseControllerException {
        if(conn == null) {
            throw new DatabaseControllerException("Connection is not ready");
        }

        try {
            return new Query(conn.prepareCall(query), query, true);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot prepare query");
        }
    }
}
