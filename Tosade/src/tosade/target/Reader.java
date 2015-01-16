/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tosade.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static tosade.Generator.toolDatabase;
import tosade.domain.SchemaTable;
import tosade.domain.SchemaTableField;
import tosade.domain.TargetSchema;
import tosade.template.Context;
import tosade.template.KeyValue;

/**
 *
 * @author Rory
 */
public class Reader {
    Context context;
    PreparedStatement preStatement1 = null;
    PreparedStatement preStatement2 = null;
    ResultSet result1 = null;
    ResultSet result2 = null;
    
    public boolean getStructure(Connection connection, TargetSchema ts){
        context = new Context(ts.platform);
        
        ArrayList<KeyValue> kvList = new ArrayList<>();
        KeyValue kv = new KeyValue();
        kv.key = "schemaName";
        kv.value = ts.name;
        kvList.add(kv);
        
        //ADD TABLES
        String sql1 = context.getTemplate("fetch_tables", kvList);
        try {
            preStatement1 = connection.prepareStatement(sql1);
            result1 = preStatement1.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Getting tables from " + ts.name + " has failed.");
            return false;
        }
        try {
            while(result1.next()){
                SchemaTable st = new SchemaTable();
                String tableName = result1.getString( context.getTableDefinition(ts.name, "name") );
                st.name = tableName;
                
                toolDatabase.insertSchemaTable(st);
                
                //ADD FIELDS OF TABLE
                String sql2 = context.getTemplate("fetch_fields") + result1.getString( context.getTableDefinition(st.name, "name") );
                try{
                    preStatement2 = connection.prepareStatement(sql2);
                    result2 = preStatement2.executeQuery();
                } catch (SQLException ex) {
                    System.out.println("Getting fields from table " + ts.name + result1.getString( context.getTableDefinition(st.name, "name") ) + " has failed.");
                    return false;
                }
                try {
                    while( result2.next() ){
                        SchemaTableField stf = new SchemaTableField();
                        stf.name = result2.getString( context.getFieldDefinition(st.name, "name") );
                        stf.type = result2.getString( context.getFieldDefinition(st.name, "type") );
                        stf.length = result2.getString( context.getFieldDefinition(st.name, "length") );
                        stf.primairykey = result2.getString( context.getFieldDefinition(st.name, "primarykey") ).equals("1");
                        stf.table_id = st.id;
                    }
                } catch (SQLException ex) {
                    System.out.println("Getting field info has failed.");
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Getting table info has failed.");
            return false;
        }
        return true;
    }
}
