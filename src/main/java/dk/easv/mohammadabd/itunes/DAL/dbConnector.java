package dk.easv.mohammadabd.itunes.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class dbConnector {
    private final SQLServerDataSource ds;

    // Constructor - Set up the SQL Server DataSource
    public dbConnector() {
        ds = new SQLServerDataSource();

        // Configure database connection properties
        ds.setServerName("EASV-DB4");      // Database server
        ds.setDatabaseName("myTunesOG");  // Database name
        ds.setPortNumber(1433);           // Port
        ds.setUser("CSe2024b_e_24");      // Username
        ds.setPassword("CSe2024bE24!24"); // Password
        ds.setTrustServerCertificate(true); // Trust server certificate
    }

    // Method to get a database connection
    public Connection getConnection() {
        try {
            // Establish connection to database
            Connection connection = ds.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection established successfully to the database.");
                return connection;
            } else {
                throw new SQLException("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            // Log error message for debugging purposes
            System.err.println("Error during connection to the database:");
            System.err.println("Server: " + ds.getServerName());
            System.err.println("Database: " + ds.getDatabaseName());
            e.printStackTrace();
            throw new RuntimeException("Unable to establish a connection to the database.", e);
        }
    }
}