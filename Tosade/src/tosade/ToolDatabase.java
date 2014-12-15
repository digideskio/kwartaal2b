/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import tosade.domain.*;

/**
 *
 * @author Jelle
 */
public class ToolDatabase {
    private Connection connect = null;
    
    public ToolDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
        try {
            connect = DriverManager.getConnection("jdbc:oracle:thin:@yuno.jelleluteijn.nl:1521:xe", "kwartaal2b","kwartaal2b");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }
    
    public ArrayList<Task> fetchTasks() {
        ArrayList<Task> values = new ArrayList<Task>();
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM task where status = 'pending'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.id = resultSet.getInt("id");
                task.type_id = resultSet.getInt("type_id");
                task.schema_id = resultSet.getInt("schema_id");
                task.status = resultSet.getString("status");
                task.datetime = resultSet.getDate("datetime");

                values.add(task);
            }
            return values;
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return values;
        }
    }
    
    /*public void updateTask(Task task) {
        
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
        
    }*/
}
