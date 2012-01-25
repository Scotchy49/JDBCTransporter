/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctransporter.converter;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import jdbctransporter.common.descriptors.ColumnDescriptor;
import jdbctransporter.common.descriptors.ForeignKeyDescriptor;
import jdbctransporter.common.descriptors.TableDescriptor;

/**
 * A helper class that does some limited and generic metadata grabbing
 * @author 200700563572
 */
public class GenericMetadataHelper {

    /**
     * get metadata from connection
     * @param dbConnection
     * @return
     * @throws SQLException
     */
    public static LinkedList<TableDescriptor> getTables(Connection dbConnection) throws SQLException {
        LinkedList<TableDescriptor> descriptors = new LinkedList<TableDescriptor>();
        DatabaseMetaData metaData = dbConnection.getMetaData();
        String types[] = {"TABLE", "VIEW"};
        ResultSet tablesRs = metaData.getTables(null, null, null, types);
        ResultSet rs = metaData.getTypeInfo();
        while (tablesRs.next()) {
            // get table basic data
            TableDescriptor descriptor = new TableDescriptor(
                    tablesRs.getString("TABLE_CAT"),
                    tablesRs.getString("TABLE_SCHEM"),
                    tablesRs.getString("TABLE_NAME"),
                    tablesRs.getString("TABLE_TYPE"),
                    tablesRs.getString("REMARKS"));

            // get table columns
            ResultSet columnsRs = metaData.getColumns(descriptor.getCatalog(), descriptor.getSchema(), descriptor.getName(), null);
            while (columnsRs.next()) {
                ColumnDescriptor columnDescriptor = new ColumnDescriptor(
                        descriptor.getName(),
                        columnsRs.getString("COLUMN_NAME"),
                        Math.abs(columnsRs.getInt("DATA_TYPE")),
                        columnsRs.getInt("COLUMN_SIZE"),
                        columnsRs.getInt("DECIMAL_DIGITS"),
                        columnsRs.getInt("NULLABLE")
                    );

                descriptor.getColumns().add(columnDescriptor);
            }
            
            // get primary keys
            ResultSet pkRs = metaData.getPrimaryKeys(descriptor.getCatalog(), descriptor.getSchema(), descriptor.getName());
            while( pkRs.next() ) {
                ColumnDescriptor pk = new ColumnDescriptor(pkRs.getString("TABLE_NAME"), pkRs.getString("COLUMN_NAME"));
                descriptor.setAsPrimaryKey(pk);
            }
            
            // get imported (foreign) keys
            ResultSet fkRs = metaData.getImportedKeys(descriptor.getCatalog(), descriptor.getSchema(), descriptor.getName());
            while(fkRs.next()) {
                ColumnDescriptor keyColumn = new ColumnDescriptor(fkRs.getString("FKTABLE_NAME"), fkRs.getString("FKCOLUMN_NAME"));
                ColumnDescriptor refColumn = new ColumnDescriptor(fkRs.getString("PKTABLE_NAME"), fkRs.getString("PKCOLUMN_NAME"));
                
                descriptor.addForeignKey(new ForeignKeyDescriptor(keyColumn, refColumn));
            }

            descriptors.add(descriptor);
        }
        return descriptors;
    }
}
