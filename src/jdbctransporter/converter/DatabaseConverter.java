/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctransporter.converter;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import jdbctransporter.common.descriptors.ColumnDescriptor;
import jdbctransporter.common.DataTypeMap;
import jdbctransporter.common.DbType;
import jdbctransporter.common.descriptors.ForeignKeyDescriptor;
import jdbctransporter.common.descriptors.TableDescriptor;

/**
 *
 * @author 200700563572
 */
public abstract class DatabaseConverter {

    protected String Q_DROP_E = "DROP TABLE {0}";
    protected String Q_CREATE_TABLE = "CREATE TABLE {0} ( '{'0'}' )";
    protected String Q_ADD_PK = "ALTER TABLE {0} ADD PRIMARY KEY ({1})";
    protected String Q_ADD_FK = "ALTER TABLE {0} ADD FOREIGN KEY ({1}) REFERENCES {2}({3})";
    // better refactor this
    public String Q_SELECT = "SELECT * FROM {0}";

    /**
     * returns a list of standard TableDescriptors
     * @param dbConnection
     * @return
     */
    public LinkedList<TableDescriptor> descriptTables(Connection dbConnection) throws SQLException {
        return GenericMetadataHelper.getTables(dbConnection);
    }

    /**
     * creates a query for this type of db given the TableDescriptors
     * @param tables
     * @return
     */
    public LinkedList<String> createDDLQuery(List<TableDescriptor> tables) {
        LinkedList<String> queries = new LinkedList<String>();

        for (TableDescriptor tableDescriptor : tables) {
            queries.addAll(createTableQueries(tableDescriptor));
        }

        return queries;
    }

    protected LinkedList<String> createTableQueries(TableDescriptor tableDescriptor) {
        LinkedList<String> queries = new LinkedList<String>();

        // creation query
        String createQry = MessageFormat.format(Q_CREATE_TABLE, tableDescriptor.getName());
        // columns query
        createQry = MessageFormat.format(createQry, createColumnsQuery(tableDescriptor.getColumns()));
        queries.add(createQry);

        //pk query
        queries.add(createPKQuery(tableDescriptor.getPrimaryKeys()));

        return queries;
    }

    protected String createColumnsQuery(List<ColumnDescriptor> columns) {
        StringBuilder qry = new StringBuilder();

        for (ColumnDescriptor columnDescriptor : columns) {
            qry.append(", ").append(createColumnQuery(columnDescriptor));
        }

        // substring from 1 to get rid of leading comma
        return qry.substring(1).toString();
    }

    protected String createColumnQuery(ColumnDescriptor columnDescriptor) {
        StringBuilder qry = new StringBuilder();
        qry.append(columnDescriptor.getLabel());
        qry.append(" ").append(DataTypeMap.getString(columnDescriptor.getType(), DbType.MYSQL));

        if (columnDescriptor.getType() > 10 && columnDescriptor.getSize() > 0) {
            qry.append("(").append(columnDescriptor.getSize()).append(")");
        }

        if (columnDescriptor.getNullable() == DatabaseMetaData.columnNoNulls) {
            qry.append(" NOT NULL");
        }
        return qry.toString();
    }

    protected String createPKQuery(List<ColumnDescriptor> primaryKeys) {
        StringBuilder primaryKeysNames = new StringBuilder();
        String tableName = "";

        for (ColumnDescriptor columnDescriptor : primaryKeys) {
            primaryKeysNames.append(",").append(columnDescriptor.getLabel());
            if (tableName.equals("")) {
                tableName = columnDescriptor.getTableName();
            }
        }

        return MessageFormat.format(Q_ADD_PK, tableName, primaryKeysNames.substring(1));
    }

    public LinkedList<String> createFKQueries(List<TableDescriptor> tables) {
        LinkedList<String> queries = new LinkedList<String>();

        for (TableDescriptor tableDescriptor : tables) {
            for (ForeignKeyDescriptor fkDescriptor : tableDescriptor.getForeignKeys()) {
                queries.add(
                        MessageFormat.format(
                        Q_ADD_FK,
                        fkDescriptor.key.getTableName(),
                        fkDescriptor.key.getLabel(),
                        fkDescriptor.ref.getTableName(),
                        fkDescriptor.ref.getLabel()));
            }
        }

        return queries;
    }

    public LinkedList<String> createDropQuery(List<TableDescriptor> tables) {
        LinkedList<String> dropQueries = new LinkedList<String>();

        for (TableDescriptor tableDescriptor : tables) {
            dropQueries.add(MessageFormat.format(Q_DROP_E, tableDescriptor.getName()));
        }

        return dropQueries;
    }
}
