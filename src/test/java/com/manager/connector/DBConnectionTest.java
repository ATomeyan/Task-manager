package com.manager.connector;

import com.manager.database.connector.Configuration;
import com.manager.database.connector.DBConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
class DBConnectionTest {

    @Test
    void getPropertyTest() {
        String driver = Configuration.getConfiguration().getValue("database.driver");

        assertEquals("com.mysql.cj.jdbc.Driver", driver, "Db driver should match");
    }

    @Test
    void getConnection() {
        DBConnector dbConnector =DBConnector.getDbConnector();
        assertNotNull(dbConnector.connection());
    }
}