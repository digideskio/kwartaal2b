/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import tosade.domain.TargetSchema;
import tosade.domain.Task;
import tosade.target.Reader;
import tosade.template.Context;

/**
 *
 * @author Jelle
 */
public class Generator {
    public static ToolDatabase toolDatabase;
    public static Context context;
    
    public static void Run() {
        toolDatabase = new ToolDatabase();
        
        ArrayList<Task> values = toolDatabase.fetchTasks();
        
        Reader reader = null;
        TargetSchema ts = null;
        Connection conn = null;
        
        try {
            reader = new Reader();
            
            ts = new TargetSchema();
            ts.username = "test";
            ts.password = "test";
            ts.hostname = "yuno.jelleluteijn.nl";
            ts.port = "1521";
            ts.platform = "Oracle";
            ts.name = "TEST";
            ts.id = 43;
            
            conn = DriverManager.getConnection("jdbc:oracle:thin:@yuno.jelleluteijn.nl:1521:xe", "test","test");
        } catch (SQLException ex) {
            System.out.println("fail");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println(reader.getStructure(conn, ts));
    }
}
