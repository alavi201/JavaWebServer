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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Base64;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;


public class Htpassword extends ConfigurationReader {
  private HashMap<String, String> passwords;
  
  //new variable
      private String fname = "";
      private static BufferedReader bf = null;
      Map<String, String> hm = null;

  public Htpassword( String filename ) throws IOException {
    super( filename );
    System.out.println( "Password file: " + filename );
    this.fname = filename;
    this.passwords = new HashMap<String, String>();
    this.load();
  }

  protected void parseLine( String line ) {
    String[] tokens = line.split( ":" );

    if( tokens.length == 2 ) {
      passwords.put( tokens[ 0 ], tokens[ 1 ].replace( "{SHA}", "" ).trim() );
    }
  }

  public boolean isAuthorized( String authInfo ) {
    // authInfo is provided in the header received from the client
    // as a Base64 encoded string.
    String credentials = new String(
      Base64.getDecoder().decode( authInfo ),
      Charset.forName( "UTF-8" )
    );

    // The string is the key:value pair username:password
    String[] tokens = credentials.split( ":" );
       
    //TODO: implement 
       return false;
  }

  private boolean verifyPassword( String username, String password ) {
    // encrypt the password, and compare it to the password stored
    // in the password file (keyed by username)
    // TODO: implemented 
       String pw = encryptClearPassword(password);
       String uname = "";
       String pword = "";
      try {
          BufferedReader bf = new BufferedReader(new FileReader(this.fname));
           try {
               String passreader = bf.readLine();
               String[] passtoken = passreader.split(":");
               
                   uname = passtoken[0];
                   pword = passtoken[1].replace("{SHA}", "").trim();
               
             
           } catch (IOException ex) {
               Logger.getLogger(Htpassword.class.getName()).log(Level.SEVERE, null, ex);
           }
      } catch (FileNotFoundException ex) {
          Logger.getLogger(Htpassword.class.getName()).log(Level.SEVERE, null, ex);
      }
         if(uname.equals(username) && pw.equals(pword)){
             System.out.println("YES");
             return true;
         }else{
             System.out.println("NO");
             return false;
         }
  }

  private String encryptClearPassword( String password ) {
    // Encrypt the cleartext password (that was decoded from the Base64 String
    // provided by the client) using the SHA-1 encryption algorithm
      
    try {
      MessageDigest mDigest = MessageDigest.getInstance( "SHA-1" );
      byte[] result = mDigest.digest( password.getBytes() );

      return Base64.getEncoder().encodeToString( result );
    } catch( Exception e ) {
      return e.getMessage();
    }
  }
  
  
  public void load() throws IOException{
       String str = "";
          hm = new HashMap<String,String>();
          try {
             bf = new BufferedReader(new FileReader(fname));
             while((str = bf.readLine()) != null){
                // str = bf.readLine();
                 
                 String[] s = str.split(":",2);
                 
                 String key = s[0];
                 String value = s[1];
                 
                     hm.put(key, value);
                 if(s.length == 2){
                     hm.put(s[0], s[1].replace("{SHA}", "").trim());
                 }
             }
             
             Set keys = hm.keySet();
             for(Iterator i = keys.iterator(); i.hasNext();){
                 String key = (String) i.next();
                 String value = (String) hm.get(key);
                 System.out.println(key + ":" + value);
             }
        } catch (FileNotFoundException ex) {
             ex.getMessage();
        }
  }
  public static void main(String[]args){
      Htpassword htp = null; 
      try {   
           htp = new Htpassword("./src/example.htpassword.txt");
           // htp.load();
           //System.out.println(htp.toString());     
      } catch (IOException ex) {
          Logger.getLogger(Htpassword.class.getName()).log(Level.SEVERE, null, ex);
      }
     // htp.verifyPassword("jrob", "helloworld");
    // System.out.println(htp.encryptClearPassword("helloworld"));
    //  htp.verifyPassword("jrob", "helloworld");
  }
}
