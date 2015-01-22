/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.controller;

import tosade.domain.ToolDatabase;
import java.util.ArrayList;
import tosade.domain.*;
import tosade.generator.Generate;
import tosade.target.TargetDatabase;
import tosade.template.Context;

/**
 *
 * @author Jelle
 */
public class Generator {
    public static Context context;
    
    public static void Run() {
        
        ArrayList<Task> values = ToolDatabase.getInstance().fetchTasks();
        
        for(Task task : values) {
            if (task.status.equals("todo")) {
                TargetSchema targetSchema = ToolDatabase.getInstance().fetchSchema(task.schema_id);
                context = new Context(targetSchema.platform);
                TaskType taskType = ToolDatabase.getInstance().fetchTaskType(task.type_id);
                if(taskType.name.equals("generate")) {
                    new Generate(task);
                }else if(taskType.name.equals("fetch") || taskType.name.equals("write")) {
                    new TargetDatabase(task);
                }
            }
        }
    }
}
