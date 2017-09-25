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
    Map<String, String> config = null;

    public Htaccess(String filename) throws IOException {
        super(filename);
        this.fname = filename;
        this.config = new HashMap<String, String>();
        this.load();
    }

    public void load() throws IOException {
        
    	try {						
			while (this.hasMoreLines()) {
	          	
            	String Line = this.nextLine();
            	
            	//System.out.println(Line);
            	
            	if(! Line.isEmpty() && Line.charAt(0)!= '#' ){	
            		String key = "";
            		String value = "";
            	
            		 String[] str = Line.split(" ");
                     
            		 key = str[0];
                     value = str[1];
                     
                     if(key.equals("AuthName")) {
                    	 value = Line.substring(key.length()+1);
                     }
                     
                     this.config.put(key, value.replace("\"", ""));
            	}
			}
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        }
    }

    public boolean isAuthorized(String username, String password) throws IOException {
       String pw = encryptClearPassword(password);
       String uname = "";
       String pword = "";
      try {
          BufferedReader bf = new BufferedReader(new FileReader("./src/example.htpassword.txt"));
           try {
               String passreader = bf.readLine();
             //  System.out.println(hm.get("AuthUserFile"));
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
           //  System.out.println("YES");
             return true;
         }else{
           //  System.out.println("NO");
             return false;
         }
    }

    private String encryptClearPassword(String password) {
    // Encrypt the cleartext password (that was decoded from the Base64 String
        // provided by the client) using the SHA-1 encryption algorithm

        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
            byte[] result = mDigest.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static void main(String[] args) throws IOException {
        Htaccess ht = new Htaccess("src/conf/_.htaccess");


        for( String key : ht.config.keySet()){
            System.out.println(key + ": " + ht.config.get(key));
        }
    }
}