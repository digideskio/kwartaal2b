/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.generator;

import tosade.domain.*;

/**
 *
 * @author Jelle
 */
public class BusinessContext {
    private IBusinessRule businessRule;
    
    public BusinessContext(String name) {
        try {
            Class<?> act = Class.forName("tosade.generator."+name+"Rule");
            businessRule = (IBusinessRule)act.newInstance();
        }catch(Exception e){
            System.out.println("Something went wrong: "+ e.getMessage());
        }
    }
    
    public String getTrigger(SchemaTableField schemaTableField, BusinessRule businessRule, BusinessRuleType businessRuleType) {
        return businessRule.getTrigger(schemaTableField, businessRule, businessRuleType);
    }
}
