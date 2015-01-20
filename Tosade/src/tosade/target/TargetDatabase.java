/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.target;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import tosade.Generator;
import tosade.domain.TargetSchema;
import tosade.domain.Task;
import tosade.domain.TaskScript;

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
        TargetSchema ts = Generator.toolDatabase.fetchSchema(task.schema_id);
        
        username = ts.username;
        password = ts.password;
        schema = ts.name;
        host = ts.hostname;
        port = ts.port;
        
        connection = DriverManager.getConnection(Generator.context.getConnectionString(ts), username, password);
    }
    
    private void executeScript(int taskId){
        ArrayList<TaskScript> scriptList = new ArrayList<>();
        
        scriptList = Generator.toolDatabase.fetchTaskScripts(taskId);
        
        for(TaskScript ts : scriptList){
            if(ts.)
        }
    }
}
