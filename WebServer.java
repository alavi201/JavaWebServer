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
		webserver.configuration = new HttpdConf("src/conf/httpd.conf");
		webserver.mimeTypes = new MimeTypes("src/conf/mime.types");
		webserver.start();
	}
	
	public void start(){
		
		this.req = new Request();
		
		ResponseFactory responseFactory = new ResponseFactory();
		try {	
			ServerSocket socket = new ServerSocket(8080);
			Socket client = null;

			while( true ) {
			      client = socket.accept();
			      Worker worker = new Worker(client, this.mimeTypes, this.configuration);
			      Thread serverThread = new Thread(worker);
			      serverThread.start();					
			    }
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
