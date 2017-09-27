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
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Htaccess extends ConfigurationReader {

    private Htpassword userFile;
    private String authType;
    private String authName;
    private String require;

    private String fname = "";
    private static BufferedReader bf = null;
    private Map<String, String> config = null;

    public Htaccess(String filename) throws IOException {
        super(filename);
        this.fname = filename;
        this.setConfig(new HashMap<String, String>());
        this.load();
    }

    public void load() throws IOException {
        
        try {                        
            while (this.hasMoreLines()) {
                  
                String Line = this.nextLine();
                
                if(! Line.isEmpty() && Line.charAt(0)!= '#' ){    
                    String key = "";
                    String value = "";
                
                    String[] str = Line.split(" ");
                     
                    key = str[0];
                    value = str[1];
                     
                    if(key.equals("AuthName")) {
                         value = Line.substring(key.length()+1);
                    }
                    
                    this.getConfig().put(key, value.replace("\"", ""));
                }
          }
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        }
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }
}