package com.manager.database.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
public final class DBConnector {

    private static final Configuration CONFIGURATION = Configuration.getConfiguration();
    private static DBConnector dbConnector;
    private final Connection connection;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final String DRIVER;

    static {
        URL = CONFIGURATION.getValue("database.url");
        USERNAME = CONFIGURATION.getValue("database.username");
        PASSWORD = CONFIGURATION.getValue("database.password");
        DRIVER = CONFIGURATION.getValue("database.driver");
    }

    private DBConnector() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnector getDbConnector() {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }

        return dbConnector;
    }

    public Connection connection() {
        return connection;
    }
}