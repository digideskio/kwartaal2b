/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tosade;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rory
 */
public class OracleTargetDatabase extends TargetDatabase{

    public OracleTargetDatabase(String username, String password, String schema, String host, String port) {
        this.host = host;
        this.password = password;
        this.username = username;
        this.port = port;
        this.schema = schema;
        
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + host + ":" + port +":xe", username, password
            );
        } catch (SQLException ex) {
            System.out.println("Connection failed.");
        }
    }
    
    

    @Override
    public boolean getStructure() {
        String sql = "select TABLE_NAME from SYS.ALL_TABLES where owner = " + schema + " order by TABLE_NAME";
        PreparedStatement preStatement = null;
        ResultSet result = null;
        try {
            preStatement = connection.prepareStatement(sql);
            result = preStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Getting tables from target has failed.");
            return false;
        }
        
        while result.
        
        return true;
    }

    @Override
    public void processStructure() {
    }
    
}
