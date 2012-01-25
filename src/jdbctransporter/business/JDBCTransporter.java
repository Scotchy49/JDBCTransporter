/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jdbctransporter.business;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbctransporter.business.data.DataReader;
import jdbctransporter.business.data.DataWriter;
import jdbctransporter.business.metadata.MetaDataReader;
import jdbctransporter.business.metadata.MetaDataWriter;
import jdbctransporter.common.DatabaseConnectionDescriptor;
import jdbctransporter.common.descriptors.TableDescriptor;
import jdbctransporter.converter.ConverterFactory;
import jdbctransporter.converter.DatabaseConverter;

/**
 *
 * @author 200700563572
 */
public class JDBCTransporter {

    private DatabaseConnectionDescriptor fromDescriptor;
    private DatabaseConnectionDescriptor toDescriptor;

    public JDBCTransporter() {
    }

    public JDBCTransporter(DatabaseConnectionDescriptor fromDescriptor, DatabaseConnectionDescriptor toDescriptor) {
        this.fromDescriptor = fromDescriptor;
        this.toDescriptor = toDescriptor;
    }

    public void transport() {
        try {
            DatabaseConverter fromConverter = ConverterFactory.getInstance().getConverter(fromDescriptor.getDatabaseType());
            DatabaseConverter toConverter = ConverterFactory.getInstance().getConverter(toDescriptor.getDatabaseType());
            
            MetaDataReader metaReader = new MetaDataReader(fromDescriptor, fromConverter);
            MetaDataWriter metaWriter = new MetaDataWriter(toDescriptor, toConverter); 
            
            LinkedList<TableDescriptor> tableDescriptors = metaReader.read();
            
            // transport metadata
            metaWriter.write(tableDescriptors);
            
            // transport data
            DataReader dataReader = new DataReader( fromDescriptor, tableDescriptors );
            DataWriter dataWriter = new DataWriter( toDescriptor, tableDescriptors, dataReader );
            
            dataWriter.write();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCTransporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(JDBCTransporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JDBCTransporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCTransporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
