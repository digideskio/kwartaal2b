/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.generator;

import java.util.ArrayList;
import tosade.controller.Generator;
import tosade.domain.*;

/**
 *
 * @author Jelle
 */
public class Generate {
    public Generate(Task task) {
        ArrayList<SchemaTable> schemaTables = Generator.toolDatabase.fetchSchemaTables(task.schema_id);
        for (SchemaTable schemaTable : schemaTables) {
            
        }
    }
}
