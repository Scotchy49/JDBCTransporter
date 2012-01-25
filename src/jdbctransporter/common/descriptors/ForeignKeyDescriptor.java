/*
 * 
 * 
 */
package jdbctransporter.common.descriptors;

/**
 *
 * @author serkan
 */
public class ForeignKeyDescriptor {
    public ColumnDescriptor key;
    public ColumnDescriptor ref;

    public ForeignKeyDescriptor() {
    }

    public ForeignKeyDescriptor(ColumnDescriptor key, ColumnDescriptor ref) {
        this.key = key;
        this.ref = ref;
    }
}
