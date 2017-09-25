import java.io.*;
import java.util.*;
import java.net.*;

public class WebServer {
	
	HttpdConf configuration;
	MimeTypes mimeTypes;
	ServerSocket socket;
	Request req;
	Response response;
	
	public static void main(String[] args) {
		
		WebServer webserver = new WebServer();
		webserver.start();
	}
	
	public void start(){
		
		this.req = new Request();
		
		ResponseFactory responseFactory = new ResponseFactory();
		try {	
			ServerSocket socket = new ServerSocket(8080);
			Socket client = null;

			
			Request ws = new Request();
			
			while( true ) {
			      client = socket.accept();
			      Worker worker = new Worker(client);
			      Thread serverThread = new Thread(worker);
			      serverThread.start();					
			    }
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
