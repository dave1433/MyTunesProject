package com.rdco24.mytunesprojectdemodave.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class dbConnector {

    private SQLServerDataSource dataSource;

    public dbConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("EASV-DB4");
        dataSource.setDatabaseName("myTunesOG");
        dataSource.setUser("CSe2024b_e_24");
        dataSource.setPassword("CSe2024bE24!2");
        dataSource.setPortNumber(1433);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLException {
        return getConnection();
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
}
