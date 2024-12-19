package com.rdco24.mytunesprojectdemodave.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class dbConnector {
    private Connection connection;
    private final SQLServerDataSource ds;

    public dbConnector()
    {
        ds = new SQLServerDataSource();
        ds.setServerName("EASV-DB4");
        ds.setDatabaseName("MyTunesOG");
        ds.setPortNumber(1433);
        ds.setUser("CSe2024b_e_24");
        ds.setPassword("CSe2024bE24!24");
        ds.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }

    public static void  main(String[] args) {
        dbConnector dbConnector = new dbConnector();
        try {
            dbConnector.getConnection();
            System.out.println("Connection Established");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Close the database connection

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}