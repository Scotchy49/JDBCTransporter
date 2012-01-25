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
public class DerbyDatabaseConverter extends DatabaseConverter {

    public DerbyDatabaseConverter() {
        Q_SELECT = "SELECT * FROM {0} OFFSET {1} ROWS FETCH NEXT {2} ROWS ONLY";
    }
    
}
