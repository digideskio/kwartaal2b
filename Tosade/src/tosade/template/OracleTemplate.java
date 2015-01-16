/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import tosade.domain.TargetSchema;

/**
 *
 * @author Jelle
 */
public class OracleTemplate implements ITemplate {
    public String getTemplate(String name) {
        String template = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("templates/oracle/" + name + ".sql"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            template = sb.toString();
            br.close();
        }catch(Exception e){
            System.out.println("Something went wrong: "+ e.getMessage());
        }
        return template;
    }
    
    public ArrayList<KeyValue> getTableDefinition(String schemaName) {
        ArrayList<KeyValue> arrayList = new ArrayList<KeyValue>();
        KeyValue keyValue;
        
        keyValue = new KeyValue();
        keyValue.key = "name";
        keyValue.value = "TABLE_NAME";
        arrayList.add(keyValue);
        return arrayList;
    }
    
    public ArrayList<KeyValue> getFieldDefinition(String fieldName) {
        ArrayList<KeyValue> arrayList = new ArrayList<KeyValue>();
        KeyValue keyValue;
        
        keyValue = new KeyValue();
        keyValue.key = "name";
        keyValue.value = "Column";
        arrayList.add(keyValue);
        
        keyValue = new KeyValue();
        keyValue.key = "type";
        keyValue.value = "Data Type";
        arrayList.add(keyValue);
        
        keyValue = new KeyValue();
        keyValue.key = "length";
        keyValue.value = "Length";
        arrayList.add(keyValue);
        
        keyValue = new KeyValue();
        keyValue.key = "primarykey";
        keyValue.value = "Primairy Key";
        arrayList.add(keyValue);
        return arrayList;
    }
    
    public String getConnectionString(TargetSchema targetSchema) {
        return "jdbc:oracle:thin:@" + targetSchema.hostname + ":" + targetSchema.port +":xe";
    }
}
