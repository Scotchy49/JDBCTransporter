/*
 * 
 * 
 */
package jdbctransporter.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author serkan
 */
public class ConnectionFactory {

    public static Connection getConnection(DatabaseConnectionDescriptor connDescriptor) throws SQLException {
        return DriverManager.getConnection(
                connDescriptor.getConnectionString(), 
                connDescriptor.getUsername(),
                connDescriptor.getPassword()
            );
    }
    
}
