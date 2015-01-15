/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade.template;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Jelle
 */
public class OracleTemplate implements ITemplate {
    public String getTemplate(String name) {
        String template = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("templates/oracle/" + name + ".sql"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            template = sb.toString();
            br.close();
        }catch(Exception e){
            System.out.println("Something went wrong: "+ e.getMessage());
        }
        return template;
    }
}
