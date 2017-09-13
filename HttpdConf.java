/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javawebserver;

/**
 *
 * @author aungphyo
 */
import java.util.*;
import java.io.*;

public class HttpdConf {
    public static void main(String[] args) throws IOException {
         BufferedReader bf = null;
        try {
             bf = new BufferedReader(new FileReader("src/conf/httpd.conf"));
        } catch (FileNotFoundException ex) {
             ex.getMessage();
        }
        String line;
        //create LinkedHashMap 
        LinkedHashMap<String,String> lhm = new LinkedHashMap<String,String>();
        LinkedHashMap<String,String> lhmsa = new LinkedHashMap<String,String>();
        LinkedHashMap<String,String> lhms = new LinkedHashMap<String,String>();
           String sa_key = null;
           String sa_value = null;
           String s_key = null;
           String s_value = null;
           while((line=bf.readLine()) != null){
                String[] str = line.split(" ");
               
                    String key = str[0];
                    String value = str[1];
                 
                    if(key.equals("ScriptAlias")){
                      
                         sa_key = str[1];
                         sa_value = str[2];
                         lhmsa.put(sa_key, sa_value);
                         
                    }else if(key.equals("Alias")){
                         s_key = str[1];
                         s_value = str[2];
                        lhms.put(s_key, s_value);
                    }else{
                        lhm.put(key, value);
                    }
               
           }
           for( String key : lhm.keySet()){
               System.out.println(key + ": " + lhm.get(key));
           }
           for(String key : lhmsa.keySet()){
               System.out.println("ScriptALias "+key + ": " + lhmsa.get(key));
           }
           for(String key : lhms.keySet()){
               System.out.println(" "+key + ": " + lhms.get(key));
           }
           bf.close();
          
        
    }
    
}
