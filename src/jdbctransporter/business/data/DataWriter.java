/*
 * 
 * 
 */
package jdbctransporter.business.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbctransporter.common.ConnectionFactory;
import jdbctransporter.common.DatabaseConnectionDescriptor;
import jdbctransporter.common.descriptors.ColumnDescriptor;
import jdbctransporter.common.descriptors.TableDescriptor;

/**
 *
 * @author serkan
 */
public class DataWriter {
    
    private DatabaseConnectionDescriptor connDescriptor;
    private LinkedList<TableDescriptor> tableDescriptors;
    private DataReader reader;
    private Connection connection;
    
    protected String Q_INSERT = "INSERT INTO {0}({1}) VALUES {2}";

    public DataWriter(DatabaseConnectionDescriptor connDescriptor, LinkedList<TableDescriptor> tableDescriptors, DataReader reader) throws SQLException {
        this.connDescriptor = connDescriptor;
        this.tableDescriptors = tableDescriptors;
        this.reader = reader;
        
        connection = ConnectionFactory.getConnection(connDescriptor);
    }

    public void write() {
        Set<TableDescriptor> triedTables = new HashSet<TableDescriptor>();
        TableDescriptor descriptor;
        while( (descriptor = tableDescriptors.pollFirst()) != null) {
            try {
                writeTable( descriptor );
                
                // succeeded a query ! let's flush the triedTables
                triedTables.clear();
            } catch( SQLException ex ) {
                // maybe a constraint exception, let's try again after
                if( !triedTables.contains(descriptor) ) {
                    Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, "Failed moving data for table {0}. Will try again.", descriptor.getName());
                    triedTables.add(descriptor);
                    tableDescriptors.addLast(descriptor);   
                } else {
                    Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, "Failed moving data for table {0} twice. Aborting.", descriptor.getName());
                }
            }
        }
    }

    private void writeTable(TableDescriptor tableDescriptor) throws SQLException {
        String columnNames = getColumnNames(tableDescriptor);
        
        ResultSet data;
        while((data = reader.read(tableDescriptor)).next()) {
            StringBuilder values = new StringBuilder();
            do {
                values.append(",(");
                for( ColumnDescriptor column : tableDescriptor.getColumns() ) {
                    String value = data.getString(column.getLabel()); 
                    if( column.getType() > 10 ) {
                        // append quotes
                        values.append("'").append(value).append("',");
                    } else {
                        values.append(value).append(",");
                    }
                }
                
                values.deleteCharAt(values.length()-1) // remove trailing comma
                        .append(")"); 
            } while(data.next());
            
            String query = MessageFormat.format(Q_INSERT, tableDescriptor.getName(), columnNames, values.substring(1));
            executeQuery(query);
        }
    }

    private void executeQuery(String qry) throws SQLException {
        Statement stmt = connection.createStatement();
        Logger.getLogger(DataReader.class.getName()).log(Level.INFO, qry);
        stmt.execute(connection.nativeSQL(qry));
    }

    private String getColumnNames(TableDescriptor tableDescriptor) {
        StringBuilder columnNames = new StringBuilder();
        for( ColumnDescriptor column : tableDescriptor.getColumns() ) {
            columnNames.append(",").append(column.getLabel());
        }
        return columnNames.substring(1);
    }
    
}
