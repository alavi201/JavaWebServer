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
import java.util.Set;

public class ConfigurationReader{
      
      static String fname = "";
      private static BufferedReader bf = null;
      Map<String, String> hm = null;
      
     
      public ConfigurationReader(String fileName){
           fname = fileName;
          // hm = new HashMap<String,String>();
      }
      
      public boolean hasMoreLine() throws IOException{
           boolean line = false;
           if(bf.readLine() != null){
               line = true;
           }
           else{
               line = false;
           }
           return line; 
      }
      
      public String nextLine() throws IOException{
           String s = "";
        
           while(bf.readLine() != null){
               s = bf.readLine();
           }
           
           return s;
      }
      
      public void load() throws IOException{
      //    String str = "";
      //    hm = new HashMap<String,String>();
          try {
             bf = new BufferedReader(new FileReader(fname));
            /* while((bf.readLine()) != null){
                 str = bf.readLine();
                 
                 String[] s = str.split(" ");
                 
                 String key = s[0];
                 String value = s[1];
                 
                 hm.put(key, value); 
             }
             
             Set keys = hm.keySet();
             for(Iterator i = keys.iterator(); i.hasNext();){
                 String key = (String) i.next();
                 String value = (String) hm.get(key);
                 System.out.println(key + "=" + value);
             } */
        } catch (FileNotFoundException ex) {
             ex.getMessage();
        }
      }
      
      
      
}
