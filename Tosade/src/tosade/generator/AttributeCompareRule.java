/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.generator;

import java.util.ArrayList;
import tosade.domain.*;
import tosade.template.Context;
import tosade.template.KeyValue;

/**
 *
 * @author Rory
 */
public class AttributeCompareRule implements IBusinessRule {
    public String getTrigger(SchemaTableField schemaTableField, BusinessRule businessRule, BusinessRuleType businessRuleType) {
        ArrayList<Operator> operators = ToolDatabase.getInstance().fetchOperators(businessRuleType.id);
        
        OperatorValue operatorValues = null;
        Operator useOperators = null;
        for(Operator operator : operators) {
            ArrayList<OperatorValue> operatorValue = ToolDatabase.getInstance().fetchOperatorValues(businessRule.id, operator.id);
            for(OperatorValue operatorvalue : operatorValue) {
                operatorValues = operatorvalue;
                useOperators = operator;
            }
        }

        String triggerOperator = "";
        boolean triggerUsed = false;
        if(businessRule.execute_insert) {
            if(triggerUsed) {
                triggerOperator = triggerOperator + ",";
            }
            triggerUsed = true;
            triggerOperator = triggerOperator + "'INS'";
        }
        if(businessRule.execute_update) {
            if(triggerUsed) {
                triggerOperator = triggerOperator + ",";
            }
            triggerUsed = true;
            triggerOperator = triggerOperator + "'UPD'";
        }
        if(businessRule.execute_delete) {
            if(triggerUsed) {
                triggerOperator = triggerOperator + ",";
            }
            triggerOperator = triggerOperator + "'DEL'";
        }
        
        String value;
        ArrayList<KeyValue> kvList = new ArrayList<>();
        if(operatorValues.is_field) {
            //value is een referentie naar een ander veld
            ArrayList<KeyValue> kvListValue = new ArrayList<>();
            int fieldId = Integer.parseInt(operatorValues.value);
            SchemaTableField attributeSchemaTableField = ToolDatabase.getInstance().fetchSchemaTableField(fieldId);
            kvListValue.add(new KeyValue("fieldName",attributeSchemaTableField.name));
            if(attributeSchemaTableField.table_id == schemaTableField.table_id) {
                //het andere veld zit in de zelfde tabel
                //tuple compare rule
                kvList.add(new KeyValue("interEntityOne",""));
                kvList.add(new KeyValue("interEntityTwo",""));
                value = Context.getInstance().getTemplate("trigger_attribute_compare_tuple", kvListValue);
            }else{
                //het andere veld zit in een andere tabel
                //inter-entity compare rule
                SchemaTable attributeSchemaTable = ToolDatabase.getInstance().fetchSchemaTable(attributeSchemaTableField.table_id);
                kvListValue.add(new KeyValue("tableName",attributeSchemaTable.name));
                kvListValue.add(new KeyValue("primairyKey",operatorValues.primairykey));
                kvListValue.add(new KeyValue("foreignKey",operatorValues.foreignkey));
                kvList.add(new KeyValue("interEntityOne",Context.getInstance().getTemplate("trigger_attribute_compare_inter_one", kvListValue)));
                kvList.add(new KeyValue("interEntityTwo",Context.getInstance().getTemplate("trigger_attribute_compare_inter_two", kvListValue)));
                value = Context.getInstance().getTemplate("trigger_attribute_compare_inter", kvListValue);
            }
        }else{
            //standaard attribute rule
            kvList.add(new KeyValue("interEntityOne",""));
            kvList.add(new KeyValue("interEntityTwo",""));
            value = operatorValues.value;
        }
        
        kvList.add(new KeyValue("triggerOperator",triggerOperator));
        kvList.add(new KeyValue("fieldName",schemaTableField.name));
        kvList.add(new KeyValue("operator",useOperators.type));
        kvList.add(new KeyValue("operatorValue",value));
        kvList.add(new KeyValue("errorMessage",businessRule.error_message));
        String rule = Context.getInstance().getTemplate("trigger_attribute_compare", kvList);
        return rule;
    }
}
