/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tosade.target;

import java.sql.Connection;
import tosade.Generator;
import tosade.domain.TaskScript;
import tosade.template.Context;

/**
 *
 * @author Rory
 */
public class Writer {
    Context context;
    
    public Writer(){
        context = Generator.context;
    }
    
    public boolean WriteScript(TaskScript taskScript, Connection conn){
        
    }
}
