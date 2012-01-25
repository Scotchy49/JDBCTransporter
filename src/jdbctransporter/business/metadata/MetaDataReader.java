/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jdbctransporter.business.metadata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbctransporter.common.ConnectionFactory;
import jdbctransporter.common.DatabaseConnectionDescriptor;
import jdbctransporter.common.descriptors.TableDescriptor;
import jdbctransporter.converter.DatabaseConverter;

/**
 *
 * @author 200700563572
 */
public class MetaDataReader {
    private DatabaseConnectionDescriptor connDescriptor;
    private DatabaseConverter converter;

    public MetaDataReader(DatabaseConnectionDescriptor connDescriptor, DatabaseConverter converter) {
        this.connDescriptor = connDescriptor;
        this.converter = converter;
    }

    public LinkedList<TableDescriptor> read() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Connection conn = ConnectionFactory.getConnection(connDescriptor);
            return converter.descriptTables(conn);
        } catch (SQLException ex) {
            Logger.getLogger(MetaDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
