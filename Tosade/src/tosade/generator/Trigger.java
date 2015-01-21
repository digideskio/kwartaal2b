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
            
            String trigger = "";
            ArrayList<SchemaTableField> schemaTablesField = Generator.toolDatabase.fetchSchemaTableFields(schemaTable.id);
            for (SchemaTableField schemaTableField : schemaTablesField) {
                ArrayList<BusinessRule> businessRules = Generator.toolDatabase.fetchBusinessRules(schemaTableField.id);
                for (BusinessRule businessRule : businessRules) {
                    BusinessRuleType businessRuleType = Generator.toolDatabase.fetchBusinessRuleType(businessRule.type_id);
                    if(businessRuleType.name.equals("")) {
                        
                    }
                }
            }
            
            
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
            kv.value = trigger;
            kvList.add(kv);
            String sql1 = Generator.context.getTemplate("trigger", kvList);
        }
    }
}
