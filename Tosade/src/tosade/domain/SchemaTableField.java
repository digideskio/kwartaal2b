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
public class SchemaTableField {
    public int id;
    public int table_id;
    public int fields_id;
    public String name;
    public String type;
    public String length;
    public boolean primairykey;
    public boolean foreignkey;
    public String code;
    public boolean autoincrement;
}
