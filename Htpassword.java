
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.io.*;

public class Htpassword extends ConfigurationReader {
  private Map<String, String> users;

  public Htpassword( String filename ) throws IOException {
    super( filename );
    this.users = new HashMap<String, String>();
    this.load();
    
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
    
    return verifyPassword(tokens[0],tokens[1]);
	        
    
    // TODO: implement this
  }

  private boolean verifyPassword( String username, String password ) {
    // encrypt the password, and compare it to the password stored
    // in the password file (keyed by username)
    // TODO: implement this
	  
	  String encryptedPassword = encryptClearPassword(password);
	  
	  System.out.println(encryptedPassword);
     
	  
	  for( String key : this.users.keySet()){
          System.out.println(key + ": " + this.users.get(key));
      }
     	if(this.users.containsKey(username) && this.users.get(username).equals(encryptedPassword)) {
     		 System.out.println("YES");
     		return true;
        }else{
            System.out.println("NO");
            return false;
        }
  }
  
  public void load() throws IOException{
	  try {						
			while (this.hasMoreLines()) {
	          	String Line = this.nextLine();
	          	
	          	//System.out.println(Line);
	          	
	          	if(! Line.isEmpty() && Line.charAt(0)!= '#' ){	
	          		String key = "";
	          		String value = "";
	          	
	          		String[] tokens = Line.split( ":" );
	
	          	    if( tokens.length == 2 ) {
	          	      this.users.put( tokens[ 0 ], tokens[ 1 ].replace( "{SHA}", "" ).trim() );
	          	    }
	          	}
			}
      } catch (FileNotFoundException ex) {
          ex.getMessage();
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
      return "";
    }
  }
  
  public static void main(String[] args) throws IOException {
      Htpassword hp = new Htpassword("src/conf/.htpasswd");
      
      for( String key : hp.users.keySet()){
          System.out.println(key + ": " + hp.users.get(key));
      }
  }
}
