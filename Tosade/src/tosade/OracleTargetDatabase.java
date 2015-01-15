/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tosade;

import java.sql.DriverManager;
import java.sql.SQLException;
import tosade.target.Database;
import tosade.target.Reader;
import tosade.target.Writer;

/**
 *
 * @author Rory
 */
public class OracleTargetDatabase extends Database{
    private Reader reader;
    private Writer writer;

    public OracleTargetDatabase(String username, String password, String schema, String host, String port) {
        this.host = host;
        this.password = password;
        this.username = username;
        this.port = port;
        this.schema = schema;
        this.reader = new OracleTargetReader();
        this.writer = new OracleTargetWriter();
        
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + host + ":" + port +":xe", username, password
            );
        } catch (SQLException ex) {
            System.out.println("Connection failed.");
        }
    }
    
        

    @Override
    public void processStructure() {
    }

    @Override
    public boolean getStructure() {
        return reader.getStructure(connection, schema);
    }
    
}