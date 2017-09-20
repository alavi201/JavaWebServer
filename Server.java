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
			      req.readRequest( client, req );
			      Resource rsrc = new Resource(req.uri, req);
			        
					String absolpath = rsrc.absolutePath();
					
					//Print(absolpath);
					
			        File file = new File(absolpath);
			        if(!file.exists()) {
			        	 if(req.request_method.equals("PUT"))
			        	 {
			        		//Print("Create file");
			        		try{
			        		    PrintWriter writer = new PrintWriter(absolpath, "UTF-8");
			        		    writer.println("The first line");
			        		    writer.println("The second line");
			        		    writer.close();
			        		    response = responseFactory.getResponse("201");
				        		//response.send();
			        		} catch (IOException e) {
			        			response = responseFactory.getResponse("500");
				        		//response.send();
			        		   // do something
			        		} 
				        		
			        	}
			        	else {
			        		//Print("404");
			        		response = responseFactory.getResponse("404");
			        		//response.send();
			        	}
			        	// do something
			        }else {
			        	String line = "";
			        	
			        	switch (req.request_method){
				        	case "GET":
				        		BufferedReader reader = new BufferedReader(new FileReader(absolpath));
					        	
				        		response = responseFactory.getResponse("200");
				        		//Print("Check last modified");
				        		//Print("Output file contents");
				        		//Print("200");
				                while ((line = reader.readLine()) != null) {
				                	//Print(line);
				                }
				                reader.close();
				        		break;
				        	case "POST":
				        		//Print("200");
				        		response = responseFactory.getResponse("200");
				        		BufferedReader reader_post = new BufferedReader(new FileReader(absolpath));
					        	
					        	
				        		//Print("Output file contents");
				        		
				                while ((line = reader_post.readLine()) != null) {
				                	//Print(line);
				                }
				                reader_post.close();
				        		//Print("200");
				        		break;
				        	case "PUT":
				        		try{
				        			//Print("Overwriting");
				        		    PrintWriter writer = new PrintWriter(absolpath, "UTF-8");
				        		    writer.println("The first line");
				        		    writer.println("The second line");
				        		    writer.println("The third line");
				        		    writer.close();
				        		    response = responseFactory.getResponse("201");
					        		//response.send();
				        		} catch (IOException e) {
				        			response = responseFactory.getResponse("500");
					        		//response.send();
				        		   // do something
				        		} 
				        		break;
				        	case "DELETE":
				        		//Print("Delete File");
				        		if(file.delete()){
				        			System.out.println(file.getName() + " is deleted!");
				        			response = responseFactory.getResponse("204");
					        		//response.send();
				        		}else{
				        			System.out.println("Delete operation is failed.");
				        			response = responseFactory.getResponse("500");
					        		//response.send();
				        		}

				        		break;
				        		
				        }
			        }

			      response.send( client);
			      client.close();
			    }
			
			//socket.close();
			
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
