/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.template;

import java.util.ArrayList;
import tosade.domain.TargetSchema;

/**
 *
 * @author Jelle
 */
interface ITemplate {
    String getTemplate(String name);
    ArrayList<KeyValue> getTableDefinition(String schemaName);
    ArrayList<KeyValue> getFieldDefinition(String fieldName);
    String getConnectionString(TargetSchema targetSchema);
}
