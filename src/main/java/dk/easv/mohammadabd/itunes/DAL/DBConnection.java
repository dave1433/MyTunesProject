package dk.easv.mohammadabd.itunes.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    // Get database connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                DBConfig dbConfig = new DBConfig();
                String dbUrl = dbConfig.getUrl(); // URL from configuration
                String dbUsername = dbConfig.getUsername();
                String dbPassword = dbConfig.getPassword();
                String dbDriver = dbConfig.getDriver();

                // Update DB URL with SSL parameters
                dbUrl += ";encrypt=true;trustServerCertificate=true";

                // Load the JDBC driver
                Class.forName(dbDriver);

                // Debugging logs
                System.out.println("Attempting database connection...");
                System.out.println("Database URL: " + dbUrl);

                // Establish connection
                connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                System.out.println("Database connection established successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("ERROR: Database driver not found. Please check the driver configuration.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("ERROR: Failed to connect to the database. Please verify the URL, username, and password.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("ERROR: Error while closing the database connection.");
                e.printStackTrace();
            }
        }
    }
}