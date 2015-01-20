/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tosade.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tosade.controller.Generator;
import tosade.domain.TaskScript;

/**
 *
 * @author Rory
 */
public class Writer {    
    public boolean WriteScript(TaskScript taskScript, Connection conn){
        PreparedStatement preStatement = null;
        String sql = taskScript.content;
        
        try {
            preStatement = conn.prepareStatement(sql);
            preStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
            taskScript.feedback = ex.getMessage();
            Generator.toolDatabase.updateTaskScript(taskScript);
            return false;
        }
        taskScript.is_done = true;
        Generator.toolDatabase.updateTaskScript(taskScript);
        return true;
    }
}
