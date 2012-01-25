/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctransporter.common;

/**
 *
 * @author 200700563572
 */
public class DatabaseConnectionDescriptor {

    private String connectionString;
    private DbType databaseType;
    private String username;
    private String password;


    public DatabaseConnectionDescriptor(DbType dbType, String connectionString, String username, String password) {
        this.databaseType = dbType;
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public DbType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DbType databaseType) {
        this.databaseType = databaseType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
