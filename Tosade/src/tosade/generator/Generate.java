/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.generator;

import tosade.Generator;
import tosade.domain.*;

/**
 *
 * @author Jelle
 */
public class Generate {
    public Generate(Task task) {
        TargetSchema targetSchema = Generator.toolDatabase.fetchSchema(task.schema_id);
    }
}
