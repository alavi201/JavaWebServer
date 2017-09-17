import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.net.*;

public class Request {

	private String uri = "";
	private String request_method = "";
	private String http_version = "";
	private Map<String, String> query_params;
	private Map<String, String> request_headers;
	private Map<String, String> config;
	private Map<String, String> Alias;
	private Map<String, String> ScriptAlias;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String request_line = "";
		File http_file = new File("src/http_request.txt");
		
		
		
	/*try {	
		ServerSocket socket = new ServerSocket(8080);
		Socket client = null;

		
		Request ws = new Request();
		
		while( true ) {
		      client = socket.accept();
		      String response = readRequest( client, ws );
		      sendResponse( client, response );
		      client.close();
		    }
	}
	catch (IOException e) {
        e.printStackTrace();
    }*/
		
		Request ws = new Request();
		
		ws.request_headers = new HashMap<String, String>();
		ws.query_params = new HashMap<String, String>();
		ws.config =  new HashMap<String, String>();
		ws.Alias =  new HashMap<String, String>();
		ws.ScriptAlias =  new HashMap<String, String>();
		
		readHttpdConf(ws);
		
		/*for (Map.Entry<String, String> entry : ws.Alias.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            System.out.println("Key: "+key+", Value: "+value);
            
            // ...
        }*/
		
		try {
	        BufferedReader buffer = new BufferedReader(new FileReader(http_file));
	        String body = "" ;
	       
	        request_line = buffer.readLine();
	        
	        int valid_request = ParseRequestLine(request_line, ws);
	        
	        //System.out.println(ws.uri);
	        //System.out.println(ws.http_version);
	        		
			if(valid_request == 1){
				ws.request_headers = ParseRequestHeaders(buffer, ws.request_headers);
				
				/*for (Map.Entry<String, String> entry : ws.request_headers.entrySet()) {
	                String key = entry.getKey();
	                String value = entry.getValue();
	                
	                System.out.println("Key: "+key+", Value: "+value);
	                
	                // ...
	            }*/
				
				if (ws.request_headers.get("Content-Length") != null) {
					body = buffer.readLine();
					//System.out.println("Body "+body);
				}
			}
			
			//Print("Original uri "+ws.uri);
			
			if(ws.Alias.containsKey(ws.uri)) {
	        	ws.uri = ws.Alias.get(ws.uri);
	        	//Print("Aliasd "+ws.uri);
	        }
	        else if(ws.ScriptAlias.containsKey(ws.uri))
	        	ws.uri = ws.ScriptAlias.get(ws.uri);
	        else
	        	ws.uri = ws.config.get("DocumentRoot") + ws.uri ;
	        
	        if(ws.uri.lastIndexOf('.') == -1)
	        	ws.uri = ws.uri + ws.config.get("DirectoryIndex");
	        
	        Print(ws.uri);
	        
	        
	        File file = new File(ws.uri);
	        if(!file.exists()) {
	        	Print("404");
	            // do something
	        }else {
	        	String line = "";
	        	
	        	switch (ws.request_method){
		        	case "GET":
		        		BufferedReader reader = new BufferedReader(new FileReader(ws.uri));
			        	
		        		//Print("Check last modified");
		        		//Print("Output file contents");
		        		Print("200");
		                while ((line = reader.readLine()) != null) {
		                	Print(line);
		                }
		        		break;
		        	case "POST":
		        		Print("200");
		        		BufferedReader reader_post = new BufferedReader(new FileReader(ws.uri));
			        	
			        	
		        		//Print("Output file contents");
		        		
		                while ((line = reader_post.readLine()) != null) {
		                	Print(line);
		                }
		        		//Print("200");
		        		break;
		        	case "PUT":
		        		Print("Create/Overwrite file");
		        		Print("201");
		        		break;
		        	case "DELETE":
		        		Print("Delete File");
		        		if(file.delete()){
		        			System.out.println(file.getName() + " is deleted!");
		        		}else{
		        			System.out.println("Delete operation is failed.");
		        		}

		        		Print("204");
		        		break;
		        		
		        }
	        }
	        
	        
			
		} catch (IOException e) {
	        e.printStackTrace();
	    }

		
	}
	
	public static int ParseRequestLine(String request_line, Request ws)
	{
		//String newline = System.getProperty("line.separator");
		
		int return_value = 1;
		
		try {
			        
	        if(request_line.isEmpty())
	        	return_value = 0;
	        
	        //System.out.println(request_line);
	        
	        String request_parts[] = request_line.split(" ");
	        
	        if(request_parts.length != 3)
	        	return_value = 0;
	        	//buffer.System.out.println("400");
	        
	        ws.request_method = request_parts[0];
			ws.uri = request_parts[1];
			ws.http_version = request_parts[2].substring(request_parts[2].indexOf("/") + 1, request_parts[2].length());
			
			if(ws.uri.contains("?")) {
				String query_string = ws.uri.substring(ws.uri.indexOf("?") + 1, ws.uri.length());
				ws.uri = ws.uri.substring(0, ws.uri.indexOf("?"));
				String query_params[] = query_string.split("&");
				
				for (String param : query_params) {
					String param_parts[] = param.split("=");
	                String key = param_parts[0];
	                String value = param_parts[1];
	                ws.query_params.put(key, value);
	                // ...
	            }
				
				
			}
			
			//System.out.println(URI);
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return return_value;
	}
	
	public static Map<String, String> ParseRequestHeaders(BufferedReader buffer, Map<String, String> request_headers)
	{
		String readLine = "" ;
		
		try {
			        
			while ((readLine = buffer.readLine()) != null) {
				//System.out.println(readLine);
				
				if("".equals(readLine))
					break;
					//System.out.println("Done headers");
				//else
				//{
				String header_parts[] = readLine.split(": ");
				String key = header_parts[0];
				String value = header_parts[1];
				request_headers.put(key, value);
				//}
			}
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return request_headers;
		
	}

	protected static String readRequest( Socket client, Request req ) throws IOException {
	    String readLine;
	    String response = "200";
	    
	    BufferedReader buffer = new BufferedReader(
	      new InputStreamReader( client.getInputStream() )
	    );
	    
	    readLine = buffer.readLine();
	    
	    int valid_request = ParseRequestLine(readLine, req);

	    if(valid_request == 1)
	    {
		    while ((readLine = buffer.readLine()) != null) {
		    	
		    	System.out.println(readLine);
				
				if("".equals(readLine))
					break;
					//System.out.println("Done headers");
				//else
				//{
				if(!readLine.contains(": ")){
					response = "400";
					break;
				}
				
				String header_parts[] = readLine.split(": ");
					
					
					String key = header_parts[0];
					String value = header_parts[1];
					System.out.println("Key "+key+", Value "+value);
				
		    }
	    }
	    else
	    	response = "400";
	    	
	    return response;
	}

	protected static void sendResponse( Socket client, String response ) throws IOException {
	    PrintWriter out = new PrintWriter( client.getOutputStream(), true );
	    //int gift = (int) Math.ceil( Math.random() * 100 );
	    //String response = "404 Gee, thanks, this is for you: " + gift;

	    out.println( response );

	    System.out.println( "I sent: " + response );
	  }

	protected static void readHttpdConf(Request ws) {
		HashMap config = new HashMap<String, String>();
		HashMap ScriptAlias = new HashMap<String, String>();
		HashMap Alias = new HashMap<String, String>();
		
		try {
			
            File httpd_file = new File("src/conf/httpd.conf");

            BufferedReader buffer = new BufferedReader(new FileReader(httpd_file));

            String line = "";

            //System.out.println("Reading file using Buffered Reader");

            while ((line = buffer.readLine()) != null) {
            	if(! line.isEmpty() && line.charAt(0)!= '#' )
            	{
            		//System.out.println(line);
            		
            		String[] str = line.split(" ");
            
            		String key = str[0];
            		String value = str[1].replace("\"", "");
             		
            		if(key.equals("ScriptAlias")){
                   
                      key = str[1];
                      value = str[2].replace("\"", "");
                      ws.ScriptAlias.put(key, value);
                      
	                 }else if(key.equals("Alias")){
	                      key = str[1];
	                      value = str[2].replace("\"", "");
	                      ws.Alias.put(key, value);
	                 }else{
	                	 ws.config.put(key, value);
	                 }
            	}
            }
		
		} catch (IOException e) {
            e.printStackTrace();
        }
	}

	protected static void Print(String string){
		System.out.println(string);
	}

}