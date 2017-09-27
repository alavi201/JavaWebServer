package configReaderPackage;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aungphyo
 */
import java.util.*;
import java.io.*;

public class HttpdConf extends ConfigurationReader {
    
    private static Map<String, String> config;
    private static Map<String, String> aliases;
    private static Map<String, String> scriptaliases;
    
    public HttpdConf (String filename){
        super(filename);
        config = new HashMap<String, String>();
        aliases = new HashMap<String, String>();
        scriptaliases = new HashMap<String, String>();
        this.load();
    }
    
    public Map getConfig() {
        return this.config;
    }
    
    public Map getAliases() {
        return this.aliases;
    }
    
    public Map getScriptAliases() {
        return this.scriptaliases;
    }
    
    public void load() {

        try {            
            
            while (this.hasMoreLines()) {
                  
                String Line = this.nextLine();
                
                if(! Line.isEmpty() && Line.charAt(0)!= '#' ) {    
                    
                    String key = "";
                    String value = "";
                
                    String[] str = Line.split(" ");
                     
                    key = str[0];
                    value = str[1];
                  
                    if(key.equals("ScriptAlias")) {
                   
                        key = str[1];
                        value = str[2];
                        this.scriptaliases.put(key, value.replace("\"", ""));
                      
                   } else if(key.equals("Alias")) {
                        
                	   key = str[1];
                       value = str[2];
                       this.aliases.put(key, value.replace("\"", ""));
                   } else{
                       this.config.put(key, value.replace("\"", ""));
                   }
               }
                
            }
            
            if(this.config.get("Listen") == null) {
                this.config.put("Listen", "8080");
            }
            
            if(this.config.get("AccessFileName") == null) { 
                this.config.put("AccessFileName",".htaccess");
            }
            
            if(this.config.get("DirectoryIndex") == null) { 
                this.config.put("DirectoryIndex","index.html");
            }
            
            if(this.config.get("LogFile") == null) { 
                this.config.put("LogFile","logs/log.txt");
            }
                       
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}