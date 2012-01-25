/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctransporter.business.metadata;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbctransporter.common.ConnectionFactory;
import java.sql.Connection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import jdbctransporter.common.DatabaseConnectionDescriptor;
import jdbctransporter.common.descriptors.TableDescriptor;
import jdbctransporter.converter.DatabaseConverter;

/**
 *
 * @author 200700563572
 */
public class MetaDataWriter {

    private DatabaseConnectionDescriptor connDescriptor;
    private DatabaseConverter converter;
    private Connection conn;
    

    public MetaDataWriter(DatabaseConnectionDescriptor toDescriptor, DatabaseConverter toConverter) throws SQLException {
        connDescriptor = toDescriptor;
        converter = toConverter;

        conn = ConnectionFactory.getConnection(connDescriptor);
    }

    public void write(List<TableDescriptor> tables) {
        // drop tables
        executeQueries( converter.createDropQuery(tables), true );
        
        // create tables
        executeQueries( converter.createDDLQuery(tables), true );
        
        //setup foreign keys
        executeQueries( converter.createFKQueries(tables), true );
    }

    private void executeFailsafeQuery(String query) {
        try {
            executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(MetaDataWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void executeQuery(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        String nativeSQL = conn.nativeSQL(query);
        Logger.getLogger(MetaDataWriter.class.getName()).log(Level.INFO, nativeSQL);
        stmt.execute(nativeSQL);
    }

    private void executeQueries(LinkedList<String> queries, boolean retry) {
        Set<String> triedQueries = new HashSet<String>();
        String query;
        while ((query = queries.pollFirst()) != null) {
            try {
                executeQuery(query);
                
                // a query successfully executed, let's try again all the others
                triedQueries.clear();
            } catch (SQLException ex) {

                if (!triedQueries.contains(query)) {
                    Logger.getLogger(MetaDataWriter.class.getName()).log(Level.SEVERE, "Failed query " + query + ". Will try again.");
                    triedQueries.add(query);
                    queries.addLast(query);
                } else {
                    Logger.getLogger(MetaDataWriter.class.getName()).log(Level.SEVERE, "Failed query " + query + " twice ! Aborting.");
                }
            }
        }
    }
}
