/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.generator;

import java.util.ArrayList;
import tosade.controller.Generator;
import tosade.domain.*;
import tosade.template.KeyValue;

/**
 *
 * @author Jelle
 */
public class Generate {
    public Generate(Task task) {
        TargetSchema targetSchema = Generator.toolDatabase.fetchSchema(task.schema_id);
        Trigger trigger = new Trigger();
        ArrayList<SchemaTable> schemaTables = Generator.toolDatabase.fetchSchemaTables(targetSchema.id);
        for (SchemaTable schemaTable : schemaTables) {
            String code = trigger.generateTriger(targetSchema, schemaTable);
            TaskScript taskScript = new TaskScript();
            taskScript.task_id = task.id;
            taskScript.content = code;
            Generator.toolDatabase.insertTaskScript(taskScript);
        }
    }
}
