/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.target;

import java.sql.Connection;

/**
 *
 * @author Jelle
 */
public abstract class Database {
    protected Connection connection = null;
    protected String username, password, schema, host, port;
    protected Reader reader;
    protected Writer writer;
    
    public abstract boolean getStructure();
    public abstract void processStructure();
}
