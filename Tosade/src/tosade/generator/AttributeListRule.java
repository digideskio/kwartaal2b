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
public class AttributeListRule implements IBusinessRule {
     public String getTrigger(SchemaTableField schemaTableField, BusinessRule businessRule, BusinessRuleType businessRuleType) {
        ArrayList<Operator> operators = ToolDatabase.getInstance().fetchOperators(businessRuleType.id);
        
        if(operators.size()>1)
            return "";
        
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
            triggerUsed = true;
            triggerOperator = triggerOperator + "'DEL'";
        }
        
        ArrayList<KeyValue> kvList = new ArrayList<>();
        kvList.add(new KeyValue("triggerOperator",triggerOperator));
        kvList.add(new KeyValue("fieldName",schemaTableField.name));
        kvList.add(new KeyValue("operator",useOperators.type));
        kvList.add(new KeyValue("operatorValue",operatorValues.value));
        kvList.add(new KeyValue("errorMessage",businessRule.error_message));
        String rule = Context.getInstance().getTemplate("trigger_attribute_list", kvList);
        return rule;
    }
}
