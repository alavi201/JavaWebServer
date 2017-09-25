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
import java.util.Set;

public class ConfigurationReader{
      
      static String fname = "";
      private static BufferedReader bf = null;
      Map<String, String> hm = null;
      String currentLine = "";
      
     
      public ConfigurationReader(String fileName){
           this.fname = fileName;
           try {
               bf = new BufferedReader(new FileReader(fname));
           }catch (FileNotFoundException ex) {
               ex.getMessage();
           }
           
          // hm = new HashMap<String,String>();
      }
      
      public boolean hasMoreLines() throws IOException{
           
    	  this.currentLine = this.bf.readLine();
    	  
    	  if(this.currentLine != null){
               return true;
           }
           else{
               return false;
           }
      }
      
      public String nextLine() throws IOException{
    	  //this.currentLine = this.bf.readLine();
          return  this.currentLine;
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