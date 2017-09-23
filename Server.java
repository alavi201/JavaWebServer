import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
	
	HttpdConf configuration;
	MimeTypes mimeTypes;
	ServerSocket socket;
	Request req;
	Response response;
	
	public static void main(String[] args) {
		
		Server s = new Server();
		s.start();
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
			      Thread t = new Thread(worker);
			      t.start();
			      //Worker.run();
			      /*req.readRequest( client, req );
			      Resource rsrc = new Resource(req.uri, req);
			      Response res; 
			      res = responseFactory.getResponse(req , rsrc);
			      res.send(client);
			      client.close();*/
					
			    }
			
			//socket.close();
			
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
