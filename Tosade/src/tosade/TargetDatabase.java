/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade;

import java.sql.Connection;

/**
 *
 * @author Jelle
 */
public abstract class TargetDatabase {
    protected Connection connection = null;
    protected String username, password, schema, host, port;
    
    public abstract boolean getStructure();
    public abstract void processStructure();
}
