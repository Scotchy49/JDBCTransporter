/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctransporter.converter;

import java.util.EnumMap;
import java.util.Map;
import jdbctransporter.common.DbType;
import jdbctransporter.converter.impl.DerbyDatabaseConverter;
import jdbctransporter.converter.impl.MySQLDatabaseConverter;

/**
 *
 * @author 200700563572
 */
public class ConverterFactory {

    private static final Map<DbType, Class<? extends DatabaseConverter>> converters =
            new EnumMap<DbType, Class<? extends DatabaseConverter>>(DbType.class);
    
    static {
        converters.put(DbType.MYSQL, MySQLDatabaseConverter.class);
        converters.put(DbType.DERBY, DerbyDatabaseConverter.class);
    }
    
    private static final ConverterFactory INSTANCE = 
            new ConverterFactory();

    private ConverterFactory() {
    }

    public static ConverterFactory getInstance() {
        return INSTANCE;
    }

    public DatabaseConverter getConverter(DbType type)
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        Class<? extends DatabaseConverter> converterClass = converters.get(type);
        if (converterClass != null) {
            return converterClass.newInstance();
        }

        throw new ClassNotFoundException(type.toString() + "DatabaseConverter");
    }
}