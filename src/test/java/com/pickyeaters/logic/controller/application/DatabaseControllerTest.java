package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseControllerTest {

    @BeforeEach
    void setUp() throws SettingsControllerException, DatabaseControllerException {
        SettingsController.getInstance().init();
        DatabaseController.getInstance().init();
    }

    @Test
    void query() throws DatabaseControllerException {
        DatabaseController.Query query = DatabaseController.getInstance().query("CALL test_database(?, ?);");
        query.setString("2");
        query.registerOutParameter(Types.VARCHAR);
        query.execute();
        String value = query.getString();
        query.close();
        assertEquals("2", value);
    }
    @Test
    void queryResultSet() throws DatabaseControllerException {
        DatabaseController.Query query = DatabaseController.getInstance().queryResultSet(
                "SELECT '1' AS ID WHERE 1=1 UNION SELECT '2' WHERE 1=1;"
        );
        query.execute();
        query.next();
        assertEquals("1", query.getString());
        query.next();
        assertEquals("2", query.getString());
        query.close();
    }
}