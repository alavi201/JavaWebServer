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

public class ConfigurationReader{
      
      static String fname = "http.conf";
      private static BufferedReader bf = null;
     
      public ConfigurationReader(String fileName){
           fname = fileName;
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
           String s = null;
           while(bf.readLine() != null){
               s = bf.readLine();
           }
           return s;
      }
      
      public void load() throws IOException{
           while(bf.readLine() != null){
               System.out.println(bf.readLine());
           }
      }
      public static void main(String[]args){
          
        try {
             bf = new BufferedReader(new FileReader(fname));
        } catch (FileNotFoundException ex) {
             ex.getMessage();
        }
          
      }
      
      
}
