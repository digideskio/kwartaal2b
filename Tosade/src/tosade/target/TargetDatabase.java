/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.target;

import java.sql.Connection;
import tosade.domain.Task;

/**
 *
 * @author Jelle
 */
public class TargetDatabase {
    protected Connection connection = null;
    protected String username, password, schema, host, port;
    protected Reader reader;
    protected Writer writer;
    
    public TargetDatabase(Task task){
        
    }
}
