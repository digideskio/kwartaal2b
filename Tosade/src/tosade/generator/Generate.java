/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.generator;

import java.util.ArrayList;
import tosade.domain.*;

/**
 *
 * @author Jelle
 */
public class Generate {
    public Generate(Task task) {
        ToolDatabase.getInstance().deleteTaskScripts(task.id);
        TargetSchema targetSchema = ToolDatabase.getInstance().fetchSchema(task.schema_id);
        Trigger trigger = new Trigger();
        ArrayList<SchemaTable> schemaTables = ToolDatabase.getInstance().fetchSchemaTables(targetSchema.id);
        for (SchemaTable schemaTable : schemaTables) {
            String code = trigger.generateTriger(targetSchema, schemaTable);
            TaskScript taskScript = new TaskScript();
            taskScript.task_id = task.id;
            taskScript.content = code;
            if(code != "") {
                System.out.println(code);
                ToolDatabase.getInstance().insertTaskScript(taskScript);
            }
        }
        //task.status = "done";
        ToolDatabase.getInstance().updateTask(task);
    }
}
