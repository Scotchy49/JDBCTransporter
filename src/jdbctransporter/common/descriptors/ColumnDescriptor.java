/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jdbctransporter.common.descriptors;

/**
 *
 * @author 200700563572
 */
public class ColumnDescriptor {

    private String tableName;
    private String label;
    private int type;
    private int size;
    private int decimalDigits;
    private int nullable;

    public ColumnDescriptor() {
    }

    public ColumnDescriptor(String tableName, String label) {
        this.tableName = tableName;
        this.label = label;
    }

    public ColumnDescriptor(String tableName, String label, int type, int size, int decimalDigits, int nullable) {
        this.tableName = tableName;
        this.label = label;
        this.type = type;
        this.size = size;
        this.decimalDigits = decimalDigits;
        this.nullable = nullable;
    }

    public String getCatalog() {
        return tableName;
    }

    public void setCatalog(String catalog) {
        this.tableName = catalog;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getType() {
        return Math.abs(type);
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColumnDescriptor other = (ColumnDescriptor) obj;
        if ((this.tableName == null) ? (other.tableName != null) : !this.tableName.equals(other.tableName)) {
            return false;
        }
        if ((this.label == null) ? (other.label != null) : !this.label.equals(other.label)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.tableName != null ? this.tableName.hashCode() : 0);
        hash = 79 * hash + (this.label != null ? this.label.hashCode() : 0);
        return hash;
    }
}
