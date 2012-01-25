/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctransporter.common.descriptors;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author 200700563572
 */
public class TableDescriptor {

    private String catalog;
    private String schema;
    private String name;
    private String type;
    private String remarks;
    private List< ColumnDescriptor > primaryKeys = new LinkedList< ColumnDescriptor >( );
    private List< ForeignKeyDescriptor > foreignKeys = new LinkedList< ForeignKeyDescriptor >( );
    private List< ColumnDescriptor > columns = new LinkedList< ColumnDescriptor >( );

    public TableDescriptor(String catalog, String schema, String name, String type, String remarks) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
        this.type = type;
        this.remarks = remarks;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ColumnDescriptor> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDescriptor> columns) {
        this.columns = columns;
    }

    public List<ColumnDescriptor> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<ColumnDescriptor> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public void setAsPrimaryKey(ColumnDescriptor columnDescriptor) {
        int columnIndex = columns.indexOf(columnDescriptor);
        if( columnIndex > -1 ) {
            columnDescriptor = columns.get(columnIndex);
            primaryKeys.add(columnDescriptor);
        }
    }

    public List<ForeignKeyDescriptor> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKeyDescriptor> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
    
    public void addForeignKey( ForeignKeyDescriptor fk ) {
        foreignKeys.add(fk);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TableDescriptor other = (TableDescriptor) obj;
        if ((this.catalog == null) ? (other.catalog != null) : !this.catalog.equals(other.catalog)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.catalog != null ? this.catalog.hashCode() : 0);
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
