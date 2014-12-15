/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import tosade.domain.*;

/**
 *
 * @author Jelle
 */
public class ToolDatabase {
    private Connection connection = null;
    
    public ToolDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@yuno.jelleluteijn.nl:1521:xe", "kwartaal2b","kwartaal2b");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
    
    public Task[] FetchTasks() {
        
    }
    
    public void updateTask(Task task) {
        
    }
    
    public TaskScript[] fetchTaskScripts(int taskId) {
        
    }
    
    public int insertTaskScript(TaskScript taskScript) {
        
    }
    
    public TargetSchema fetchSchema(int id) {
        
    }
    
    public SchemaTable fetchSchemaTable(int id) {
        
    }
    
    public SchemaTable[] fetchSchemaTables(int schemaId) {
        
    }
    
    public void deleteSchemaTables(int id) {
        
    }
    
    public void updateSchemaTables(SchemaTable schemaTable) {
        
    }
    
    public int insertSchemaTable(SchemaTable schemaTable) {
        
    }
    
    public SchemaTableField fetchSchemaTableField(int id) {
        
    }
    
    public SchemaTableField[] fetchSchemaTableFields(int schemaTableId) {
        
    }
    
    public void deleteSchemaTableField(int id) {
        
    }
    
    public void updateSchemaTableField(SchemaTableField schemaTableField) {
        
    }
    
    public int insertSchemaTableField(SchemaTableField schemaTableField) {
        
    }
}
