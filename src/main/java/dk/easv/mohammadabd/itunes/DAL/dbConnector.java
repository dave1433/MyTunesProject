package dk.easv.mohammadabd.itunes.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class dbConnector {
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
        try {
            Connection connection = ds.getConnection();
            System.out.println("Connection Established");
            return connection;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
