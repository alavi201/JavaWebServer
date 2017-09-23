import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class FourHundredAndFour extends Response{

	FourHundredAndFour(Resource resource){
		super(resource);
		this.code = 404;
		this.reasonPhrase = "File not found";
	}
	public void send(Socket client) throws IOException{
		String response = "";
	    PrintWriter out = new PrintWriter( client.getOutputStream(), true );
	    //int gift = (int) Math.ceil( Math.random() * 100 );
	    //String response = "404 Gee, thanks, this is for you: " + gift;
	    
	    response = this.code+" "+this.reasonPhrase+"\n";
	    		

	    out.println("HTTP/1.1 "+response+"\r\n"+defaultHeaders()+"Content-Type: text/HTML\r\nContent-Length: 50\r\n\n<html><head><title>Page not found</title></head><body>The page was not found.</body></html>");
	}
	/*public void send() {
		System.out.println(this.code);
	}*/
}
