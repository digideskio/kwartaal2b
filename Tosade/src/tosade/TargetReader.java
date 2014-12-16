/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tosade;

import java.sql.Connection;

/**
 *
 * @author Rory
 */
public abstract class TargetReader {
    public abstract boolean getStructure(Connection connection, String schema);
}
