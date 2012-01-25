/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jdbctransporter.converter.impl;


import jdbctransporter.converter.DatabaseConverter;

/**
 *
 * @author 200700563572
 */
public class MySQLDatabaseConverter extends DatabaseConverter{

    public MySQLDatabaseConverter() {
        Q_DROP_E = "DROP TABLE IF EXISTS `{0}`";
        Q_CREATE_TABLE = Q_CREATE_TABLE + " ENGINE=InnoDB";
        Q_ADD_FK = "ALTER TABLE {0} ADD FOREIGN KEY {0}({1}) REFERENCES {2}({3})";
        Q_SELECT = "SELECT * FROM {0} LIMIT {1},{2}";
    }
    
}
