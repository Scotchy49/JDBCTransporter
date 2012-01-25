/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctransporter.common;

/**
 *
 * @author 200700563572
 */
public class DataTypeMap {

    private static String map[][] = {
        {}, // index begins at 1
        {"BIT", "TINYINT(1)", "BOOLEAN", "BOOLEAN", "CHAR FOR BIT DATA", " BIT", "BOOLEAN", "", "BIT", " BOOLEAN"},
        {"TINYINT", "TINYINT", "SMALLINT", "SMALLINT", "SMALLINT", "TINYINT", "SMALLINT ", "SMALLINT ", "TINYINT", "SMALLINT"},
        {"SMALLINT", "SMALLINT", "SMALLINT", "SMALLINT", "SMALLINT ", "SMALLINT", "SMALLINT", "SMALLINT", "SMALLINT", "SMALLINT"},
        {"INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER ", "INTEGER", "INTEGER", "INTEGER"},
        {"BIGINT", "BIGINT", "BIGINT", "NUMERIC", "BIGINT", "BIGINT", "INTEGER", "BIGINT", "BIGINT", "NUMERIC"},
        {"FLOAT", "FLOAT", "DOUBLE PRECISION", "FLOAT", "FLOAT", "FLOAT", "FLOAT", "FLOAT", "FLOAT", "FLOAT"},
        {"DOUBLE", "DOUBLE", "DOUBLE PRECISION", "DOUBLE PRECISION", "DOUBLE", "DOUBLE PRECISION ", "DOUBLE PRECISION", "DOUBLE", "DOUBLE PRECISION", "DOUBLE PRECISION"},
        {"REAL", "REAL", "REAL", "REAL", "REAL", "REAL", "DOUBLE PRECISION", "REAL ", "REAL", "REAL"},
        {"NUMERIC", "NUMERIC", "NUMERIC", "NUMERIC", "NUMERIC", "NUMERIC", "NUMERIC", "NUMERIC", "NUMERIC", "NUMERIC "},
        {"DECIMAL", "DECIMAL", "NUMERIC", "DECIMAL", "DECIMAL", "DECIMAL", "DECIMAL", "DECIMAL", "DECIMAL", "DECIMAL"},
        {"CHAR", "CHAR", "CHAR", "CHAR", "CHAR", "CHAR", "CHAR", "CHAR", "CHAR", "CHAR"},
        {"VARCHAR", "VARCHAR", "VARCHAR", "VARCHAR2", "VARCHAR", "VARCHAR", "VARCHAR", "VARCHAR", "VARCHAR", "VARCHAR"},
        {"LONGVARCHAR", "VARCHAR", "VARCHAR", "LONG", "LONG VARCHAR", "TEXT", "LONG", "LONG VARCHAR", "LONGVARCHAR", "CLOB"},
        {"DATE", "DATE", "DATE", "DATE", "DATE", "DATETIME ", "DATE", "DATE", "DATE ", "DATE"},
        {"TIME", "TIME", "TIME", "DATE", "TIME", "DATETIME", "TIME", "TIME", "TIME ", "TIME"},
        {"TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP"},
        {"BINARY", "BINARY", "BYTEA", "RAW", "CHAR [n] FOR BIT DATA", "BINARY", "BLOB", "CHAR [n] FOR BIT DATA", "BINARY", "BLOB"},
        {"VARBINARY", "VARBINARY", "BYTEA", "LONG RAW", "VARCHAR [] FOR BIT DATA", "VARBINARY", "BLOB", "VARCHAR [] FOR BIT DATA", "VARBINARY", "BLOB"},
        {"LONGVARBINARY", "VARBINARY", "BYTEA", "LONG RAW", "LONG VARCHAR FOR BIT DATA", "IMAGE", "BLOB", "LONG VARCHAR FOR BIT DATA", "LONGVARBINARY", "BLOB"},
        {"OTHER", "BLOB", "BYTEA", "BLOB", "BLOB", "IMAGE", "BLOB", "BLOB", "OTHER", "BLOB"},
        {"JAVA_OBJECT", "BLOB", "BYTEA", "BLOB", "BLOB ", "IMAGE", "BLOB", "BLOB", "OBJECT", "BLOB"},
        {"BLOB", "BLOB", "BYTEA", "BLOB", "BLOB", "IMAGE", "BLOB", "BLOB ", "OBJECT", "BLOB"},
        {"CLOB", "TEXT", "TEXT", "CLOB", "CLOB", " TEXT", "CLOB", "CLOB", "OBJECT", "CLOB"}};

    public static String getString(int sqlType, DbType targetDbType) {
        if( sqlType > 23 ) {
            // out of bounds ! return just plain string
            return "VARCHAR";
        } else {
            return map[sqlType][targetDbType.ordinal()];
        }
        
    }
}
