/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade;

import java.util.ArrayList;
import tosade.domain.Task;

/**
 *
 * @author Jelle
 */
public class Generator {
    public static ToolDatabase toolDatabase;
    
    public static void Run() {
        toolDatabase = new ToolDatabase();
        
        ArrayList<Task> values = toolDatabase.fetchTasks();
    }
}
