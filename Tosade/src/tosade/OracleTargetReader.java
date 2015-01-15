/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tosade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static tosade.Generator.toolDatabase;
import tosade.domain.SchemaTable;
import tosade.domain.SchemaTableField;
import tosade.target.Reader;

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
        //ADD TABLES
        try {
            while(result1.next()){
                SchemaTable st = new SchemaTable();
                String tableName = result1.getString("TABLE_NAME");
                st.name = result1.getString(tableName);
                
                toolDatabase.insertSchemaTable(st);
                
                //ADD FIELDS OF TABLE
                String sql2 = "DESCRIBE " + result1.getString("Table_NAME");
                try{
                    preStatement2 = connection.prepareStatement(sql2);
                    result2 = preStatement2.executeQuery();
                } catch (SQLException ex) {
                    System.out.println("Getting fields from table " + schema + result1.getString("TABLE_NAME") + " has failed.");
                    return false;
                }
                
                try {
                    while( result2.next() ){
                        SchemaTableField stf = new SchemaTableField();
                        stf.name = result2.getString("Column");
                        stf.type = result2.getString("Data Type");
                        stf.length = result2.getString("Length");
                        if(result2.getString("Primary Key").equals("1"))
                            stf.primairykey = true;
                        else
                            stf.primairykey = false;
                    }
                } catch (SQLException ex) {
                    System.out.println("Getting field info has failed.");
                    return false;
                }
                
            }
        } catch (SQLException ex) {
            System.out.println("Getting table info has failed.");
            return false;
        }
        
        
        
        return true;
    }
}
