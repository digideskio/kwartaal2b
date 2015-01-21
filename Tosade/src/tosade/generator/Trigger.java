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
    KeyValue kv;
            
    public Trigger(TargetSchema targetSchema) {
        ArrayList<SchemaTable> schemaTables = Generator.toolDatabase.fetchSchemaTables(targetSchema.id);
        for (SchemaTable schemaTable : schemaTables) {
            
            String trigger = "";
            ArrayList<SchemaTableField> schemaTablesField = Generator.toolDatabase.fetchSchemaTableFields(schemaTable.id);
            for (SchemaTableField schemaTableField : schemaTablesField) {
                ArrayList<BusinessRule> businessRules = Generator.toolDatabase.fetchBusinessRules(schemaTableField.id);
                for (BusinessRule businessRule : businessRules) {
                    BusinessRuleType businessRuleType = Generator.toolDatabase.fetchBusinessRuleType(businessRule.type_id);
                    if(businessRuleType.code.equals("ARNG")) {
                        String triggerPassed = "";
                        ArrayList<Operator> operators = Generator.toolDatabase.fetchOperators(businessRuleType.id);
                        for(Operator operator : operators) {
                            OperatorValue operatorValue = Generator.toolDatabase.fetchOperatorValue(businessRule.id, operator.id);
                            ArrayList<KeyValue> kvListValue = new ArrayList<>();
                            kv = new KeyValue();
                            kv.key = "fieldName";
                            kv.value = schemaTableField.name;
                            kvListValue.add(kv);
                            kv = new KeyValue();
                            kv.key = "operator";
                            kv.value = operator.name;
                            kvListValue.add(kv);
                            kv = new KeyValue();
                            kv.key = "operatorValue";
                            kv.value = operatorValue.value;
                            kvListValue.add(kv);
                            trigger = trigger + Generator.context.getTemplate("trigger_attribute_range_passed", kvListValue);
                        }
                        
                        String triggerOperator = "";
                        boolean triggerUsed = false;
                        if(businessRule.execute_insert) {
                            if(!triggerUsed) {
                                triggerOperator = triggerOperator + ",";
                            }
                            triggerOperator = triggerOperator + "'INS'";
                        }
                        if(businessRule.execute_update) {
                            if(!triggerUsed) {
                                triggerOperator = triggerOperator + ",";
                            }
                            triggerOperator = triggerOperator + "'UPD'";
                        }
                        if(businessRule.execute_delete) {
                            if(!triggerUsed) {
                                triggerOperator = triggerOperator + ",";
                            }
                            triggerOperator = triggerOperator + "'DEL'";
                        }
                        ArrayList<KeyValue> kvList = new ArrayList<>();
                        kv = new KeyValue();
                        kv.key = "triggerOperator";
                        kv.value = triggerOperator;
                        kvList.add(kv);
                        kv = new KeyValue();
                        kv.key = "errorMessage";
                        kv.value = businessRule.error_message;
                        kvList.add(kv);
                        kv = new KeyValue();
                        kv.key = "triggerPassed";
                        kv.value = triggerPassed;
                        kvList.add(kv);
                        trigger = trigger + Generator.context.getTemplate("trigger_attribute_range", kvList);
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
