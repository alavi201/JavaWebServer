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
import java.util.Set;

public class ConfigurationReader{
      
      static String filename = "";
      private static BufferedReader bufferreader = null;
      String currentLine = "";
      
      public ConfigurationReader(String fileName){
           
    	  this.filename = fileName;
           
          try {
        	   bufferreader = new BufferedReader(new FileReader(this.filename));
          }catch (FileNotFoundException ex) {
               ex.getMessage();
          }
      }
      
      public boolean hasMoreLines() throws IOException{
           
          this.currentLine = this.bufferreader.readLine();
          
          if(this.currentLine != null){
               return true;
          }
          else{
               return false;
          }
      }
      
      public String nextLine() throws IOException{
          return  this.currentLine;
      }
      
      public void load() throws IOException{
      }  
}