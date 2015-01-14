/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tosade;

import tosade.target.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tosade.domain.SchemaTable;

/**
 *
 * @author Rory
 */
public class OracleTargetReader extends Reader {

    public OracleTargetReader() {
    }
    
    
    
    @Override
    public boolean getStructure(Connection connection, String schema) {
        String sql1 = "select TABLE_NAME from SYS.ALL_TABLES where owner = " + schema + " order by TABLE_NAME";
        String sql2 = "";
        PreparedStatement preStatement1 = null;
        PreparedStatement preStatement2 = null;
        ResultSet result1 = null;
        ResultSet result2 = null;
        try {
            preStatement1 = connection.prepareStatement(sql1);
            result1 = preStatement1.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Getting tables from target has failed.");
            return false;
        }
        
        try {
            while(result1.next()){
                SchemaTable st = new SchemaTable();
                String tableName = result1.getString("TABLE_NAME");
                st.name = result1.getString(tableName);
                
                Generator.toolDatabase.insertTable(st);
                
                //FIELDS OF TABLE
                
            }
        } catch (SQLException ex) {
            System.out.println("Getting table info has failed.");
            return false;
        }
        
        
        
        return true;
    }
}
