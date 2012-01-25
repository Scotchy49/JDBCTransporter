/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctransporter;

import jdbctransporter.common.DatabaseConnectionDescriptor;
import jdbctransporter.business.JDBCTransporter;
import jdbctransporter.common.DbType;

/**
 *
 * @author 200700563572
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        DatabaseConnectionDescriptor toDescriptor =
                new DatabaseConnectionDescriptor(
                DbType.MYSQL,
                "jdbc:mysql://localhost:3306/transport",
                "transport",
                "");

        DatabaseConnectionDescriptor fromDescriptor =
                new DatabaseConnectionDescriptor(
                DbType.DERBY,
                "jdbc:derby://localhost:1527/transport",
                "app",
                "app");

        JDBCTransporter transporter = new JDBCTransporter(fromDescriptor, toDescriptor);
        transporter.transport();
    }

}
