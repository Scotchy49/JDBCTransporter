/*
 * 
 * 
 */
package jdbctransporter.business.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbctransporter.common.ConnectionFactory;
import jdbctransporter.common.DatabaseConnectionDescriptor;
import jdbctransporter.common.descriptors.TableDescriptor;
import jdbctransporter.converter.ConverterFactory;

/**
 *
 * @author serkan
 */
public class DataReader {

    private DatabaseConnectionDescriptor connDescriptor;
    private List<TableDescriptor> tableDescriptors;
    private Connection connection;
    private Integer fetchSize = 1000;
    private Map< TableDescriptor, Integer> cursors = new HashMap< TableDescriptor, Integer>();
    
    public DataReader(DatabaseConnectionDescriptor connDescriptor, List<TableDescriptor> tableDescriptors) throws SQLException {
        this.connDescriptor = connDescriptor;
        this.tableDescriptors = tableDescriptors;

        connection = ConnectionFactory.getConnection(connDescriptor);
    }
    
    public Integer getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(Integer fetchSize) {
        // reset cursors
        cursors = new HashMap< TableDescriptor, Integer>();
        
        this.fetchSize = fetchSize;
    }

    public ResultSet read(TableDescriptor tableDescriptor) throws SQLException {
        Statement stmt = connection.createStatement();

        if (!cursors.containsKey(tableDescriptor)) {
            cursors.put(tableDescriptor, 0);
        } else {
            cursors.put(tableDescriptor, cursors.get(tableDescriptor) + fetchSize);
        }

        Integer currentCursor = cursors.get(tableDescriptor);

        String qry = "";
        try {
            qry = MessageFormat.format(
                    ConverterFactory.getInstance().getConverter(connDescriptor.getDatabaseType()).Q_SELECT,
                    tableDescriptor.getName(), String.valueOf(currentCursor), String.valueOf(fetchSize));
        } catch (InstantiationException ex) {
            Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(DataReader.class.getName()).log(Level.INFO, qry);
        
        return stmt.executeQuery(qry);
    }
}
