/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.domain;

/**
 *
 * @author Jelle
 */
public class BusinessRule {
    public int id;
    public int type_id;
    public int field_id;
    public int error_id;
    public boolean to_generate;
    public String trigger_event;
    public String error_message;
}
