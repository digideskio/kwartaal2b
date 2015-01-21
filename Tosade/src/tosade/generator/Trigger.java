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
public class Trigger {
    public Trigger(TargetSchema targetSchema) {
        ArrayList<SchemaTable> schemaTables = Generator.toolDatabase.fetchSchemaTables(targetSchema.id);
        for (SchemaTable schemaTable : schemaTables) {
            
            
            ArrayList<KeyValue> kvList = new ArrayList<>();
            KeyValue kv = new KeyValue();
            kv.key = "schemaCode";
            kv.value = targetSchema.code;
            kvList.add(kv);
            kv = new KeyValue();
            kv.key = "tableCode";
            kv.value = schemaTable.code;
            kvList.add(kv);
            kv = new KeyValue();
            kv.key = "tableName";
            kv.value = schemaTable.name;
            kvList.add(kv);
            kv = new KeyValue();
            kv.key = "triggers";
            kv.value = "";
            kvList.add(kv);
            String sql1 = Generator.context.getTemplate("trigger", kvList);
        }
    }
}
