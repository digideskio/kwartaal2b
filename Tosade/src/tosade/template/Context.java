/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.template;

/**
 *
 * @author Jelle
 */
public class Context {
    private ITemplate platform;
    
    public Context(String name) {
        try {
            Class<?> act = Class.forName("tosade.template."+name+"Template");
            platform = (ITemplate)act.newInstance();
        }catch(Exception e){
            System.out.println("Something went wrong: "+ e.getMessage());
        }
    }
    
    public String getTemplate(String name) {
        return platform.getTemplate(name);
    }
}
