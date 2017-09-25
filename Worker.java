import java.io.*;
import java.util.*;
import java.net.*;

public class Worker implements Runnable {
	
	private Socket client;
	private MimeTypes mimeTypes;
	private HttpdConf configuration;
	
	Worker (Socket client, MimeTypes mimeTypes, HttpdConf configuration){
		this.client = client;	
		this.configuration = configuration;
		this.mimeTypes = mimeTypes;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ResponseFactory responseFactory = new ResponseFactory();
		Request req = new Request();
		
		Logger log = new Logger( (String) this.configuration.getConfig().get("LogFile"));
		
		try {	
		req.readRequest( client, req );
	      Resource rsrc = new Resource(req.uri, this.configuration);
	      Response res; 
	      res = responseFactory.getResponse(req , rsrc);
	      
	      res.send(client);
	      
	      log.write(req, res, this.client);
	      client.close();
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }

	}

}
