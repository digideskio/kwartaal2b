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
public class AtributeRangeRule implements IBusinessRule {
    public String getTrigger(SchemaTableField schemaTableField, BusinessRule businessRule, BusinessRuleType businessRuleType) {
        String triggerPassed = "";
        ArrayList<Operator> operators = Generator.toolDatabase.fetchOperators(businessRuleType.id);
        for(Operator operator : operators) {
            OperatorValue operatorValue = Generator.toolDatabase.fetchOperatorValue(businessRule.id, operator.id);
            ArrayList<KeyValue> kvListValue = new ArrayList<>();
            kvListValue.add(new KeyValue("fieldName",schemaTableField.name));
            kvListValue.add(new KeyValue("operator",operator.name));
            kvListValue.add(new KeyValue("operatorValue",operatorValue.value));
            kvListValue.add(new KeyValue("errorMessage",businessRule.error_message));
            triggerPassed = triggerPassed + Generator.context.getTemplate("trigger_attribute_range_passed", kvListValue);
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
        kvList.add(new KeyValue("triggerOperator",triggerOperator));
        kvList.add(new KeyValue("triggerPassed",triggerPassed));
        return Generator.context.getTemplate("trigger_attribute_range", kvList);
    }
}
