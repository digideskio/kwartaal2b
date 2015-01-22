/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.target;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tosade.controller.Generator;
import tosade.domain.TargetSchema;
import tosade.domain.Task;
import tosade.domain.TaskScript;
import tosade.domain.TaskType;
import tosade.domain.ToolDatabase;

/**
 *
 * @author Rory
 */
public class TargetDatabase {
    private Connection connection = null;
    private String username, password, schema, host, port;
    private Reader reader;
    private Writer writer;
    
    
    public TargetDatabase(Task task){
        TargetSchema ts = ToolDatabase.getInstance().fetchSchema(task.schema_id);
        
        username = ts.username;
        password = ts.password;
        schema = ts.name;
        host = ts.hostname;
        port = ts.port;
        boolean error = false;
        
        try {
            connection = DriverManager.getConnection(Generator.context.getConnectionString(ts), username, password);
        } catch (SQLException ex) {
            Logger.getLogger(TargetDatabase.class.getName()).log(Level.SEVERE, null, ex);
            task.feedback = ex.getMessage();
            task.status = "error";
            ToolDatabase.getInstance().updateTask(task);
            error = true;
        }
        
        if(!error)
            executeScript(task);
    }
    
    private void executeScript(Task task){
        TaskType taskType = ToolDatabase.getInstance().fetchTaskType(task.type_id);
        if(taskType.name.equals("write")) {
            writer = new Writer();
            ArrayList<TaskScript> scriptList = ToolDatabase.getInstance().fetchTaskScripts(task.id);
            
            for(TaskScript ts : scriptList){
                if(!ts.is_done){
                    if(writer.WriteScript(ts, connection))
                        task.status = "done";
                    else
                        task.status = "error";
                    ToolDatabase.getInstance().updateTask(task);
                }
            }
        }else if(taskType.name.equals("fetch")){
            //LATER TO COME
        }
    }
}
