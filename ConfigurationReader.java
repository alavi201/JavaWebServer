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

public class ConfigurationReader {
      
      String fname;
      
      public ConfigurationReader(String fileName){
           fname = fileName;
      }
      
      public boolean hasMoreLine(){
          
           return false; 
      }
      
      public String nextLine(){
           
           return "";
      }
      
      public void load(){
          
      }
      public static void main(String[]args){
          
      }
      
      
}
