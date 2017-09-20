import java.io.*;
import java.net.Socket;
import java.util.*;

public abstract class Response {

	public int code = 0;
	public String reasonPhrase = "";
	Resource resource;
	
	/*Response(Resource resource){
		this.resource = resource;
	}*/
	
	protected void send( Socket client) throws IOException {
		String response = "";
	    PrintWriter out = new PrintWriter( client.getOutputStream(), true );
	    //int gift = (int) Math.ceil( Math.random() * 100 );
	    //String response = "404 Gee, thanks, this is for you: " + gift;
	    
	    response = this.code+" "+this.reasonPhrase+"\n";
	    		

	    out.println( response );

	    System.out.println( "I sent: " + response );
	  }
		
}
