import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class FourHundredAndThree extends Response{

	FourHundredAndThree(Resource resource){
		super(resource);
		this.code = 403;
		this.reasonPhrase = "Forbidden";
	}
	
	public void send(Socket client) throws IOException{
		
		String response = "";
	    PrintWriter out = new PrintWriter( client.getOutputStream(), true );
	    OutputStream socketOutputStream = client.getOutputStream();
		
	    //this.responseHeaders.put("WWW-Authenticate","Basic");
			
		socketOutputStream.write((getResponseline() + getHeaders()+"\r\n").getBytes());
	}
}
