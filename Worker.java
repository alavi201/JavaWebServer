import java.io.*;
import java.util.*;
import java.net.*;

public class Worker implements Runnable {
	
	private Socket client;
	private MimeTypes mimes;
	private HttpdConf config;
	
	Worker (Socket client){
		this.client = client;	
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ResponseFactory responseFactory = new ResponseFactory();
		Request req = new Request();
		
		try {	
		req.readRequest( client, req );
	      Resource rsrc = new Resource(req.uri, req);
	      Response res; 
	      res = responseFactory.getResponse(req , rsrc);
	      res.send(client);
	      client.close();
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }

	}

}
