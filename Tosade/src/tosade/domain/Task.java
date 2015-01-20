/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.domain;

import java.sql.Timestamp;

/**
 *
 * @author Jelle
 */
public class Task {
    public int id;
    public int type_id;
    public int schema_id;
    public String status;
    public String feedback;
    public Timestamp datetime;
}
