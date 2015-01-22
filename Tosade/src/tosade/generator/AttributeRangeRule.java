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
 * @author Jelle
 */
public class AttributeRangeRule implements IBusinessRule {
    public String getTrigger(SchemaTableField schemaTableField, BusinessRule businessRule, BusinessRuleType businessRuleType) {
        String triggerPassed = "";
        ArrayList<Operator> operators = ToolDatabase.getInstance().fetchOperators(businessRuleType.id);
        ArrayList<OperatorValue> operatorValues = new ArrayList<OperatorValue>();
        ArrayList<Operator> useOperators = new ArrayList<Operator>();
        for(Operator operator : operators) {
            ArrayList<OperatorValue> operatorValue = ToolDatabase.getInstance().fetchOperatorValues(businessRule.id, operator.id);
            for(OperatorValue operatorvalue : operatorValue) {
                operatorValues.add(operatorvalue);
                useOperators.add(operator);
            }
        }
        if (operatorValues.size() != 2) {
            return "";
        }
        int i = 0;
        String value = "";
        
        for(Operator operator : useOperators) {
            i++;
            OperatorValue operatorValue = ToolDatabase.getInstance().fetchOperatorValue(businessRule.id, operator.id);
            ArrayList<KeyValue> kvListValue = new ArrayList<>();
            kvListValue.add(new KeyValue("fieldName",schemaTableField.name));
            kvListValue.add(new KeyValue("operator",operator.type));
            kvListValue.add(new KeyValue("operatorValue",operatorValue.value));
            if(i == 2) {
                if (Integer.parseInt(operatorValue.value) > Integer.parseInt(value)) {
                    if(operator.type.equals("<") || operator.type.equals("<=")) {
                        triggerPassed = triggerPassed + " AND ";
                    }else{
                        triggerPassed = triggerPassed + " OR ";
                    }
                }else{
                    if(operator.type.equals(">") || operator.type.equals(">=")) {
                        triggerPassed = triggerPassed + " AND ";
                    }else{
                        triggerPassed = triggerPassed + " OR ";
                    }
                }
            }
            value = operatorValue.value;
            triggerPassed = triggerPassed + Context.getInstance().getTemplate("trigger_attribute_range_passed", kvListValue);
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
            triggerUsed = true;
            triggerOperator = triggerOperator + "'DEL'";
        }
        ArrayList<KeyValue> kvList = new ArrayList<>();
        kvList.add(new KeyValue("triggerOperator",triggerOperator));
        kvList.add(new KeyValue("triggerPassed",triggerPassed));
        kvList.add(new KeyValue("errorMessage",businessRule.error_message));
        String rule = Context.getInstance().getTemplate("trigger_attribute_range", kvList);
        return rule;
    }
}
