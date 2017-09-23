import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.net.*;

public class Request {

	public String uri = "";
	public String request_method = "";
	private String http_version = "";
	public String body = "";
	public String query_params;
	public Map<String, String> request_headers;
	public Map<String, String> config;
	public Map<String, String> Alias;
	public Map<String, String> ScriptAlias;
	
	
		
	public static int ParseRequestLine(String request_line, Request ws)
	{
		//String newline = System.getProperty("line.separator");
		
		int return_value = 1;
		
		try {
			        
	        if(request_line.isEmpty())
	        	return_value = 0;
	        
	        System.out.println(request_line);
	        
	        String request_parts[] = request_line.split(" ");
	        
	        if(request_parts.length != 3)
	        	return_value = 0;
	        	//buffer.System.out.println("400");
	        
	        ws.request_method = request_parts[0];
			ws.uri = request_parts[1];
			ws.http_version = request_parts[2].substring(request_parts[2].indexOf("/") + 1, request_parts[2].length());
			
			if(ws.uri.contains("?")) {
				ws.uri = ws.uri.substring(0, ws.uri.indexOf("?"));
				String query_string = ws.uri.substring(ws.uri.indexOf("?") + 1, ws.uri.length());
				ws.query_params = query_string;
				
				
			}
			
			//System.out.println(URI);
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return return_value;
	}
	
	public static Map<String, String> ParseRequestHeaders(InputStream inputstream, Map<String, String> request_headers)
	{
		String readLine = "" ;
		
		try {
			        
			while ((readLine = readline(inputstream)) != null) {
				System.out.println(readLine);
				
				if(readLine.equals(""))
				{
					break;
				}
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

	protected void readRequest( Socket client, Request req ) throws IOException {
	    String readLine;
	    String response = "200";
	    
	    req.request_headers = new HashMap<String, String>();
	    req.query_params = "";
	    req.config =  new HashMap<String, String>();
	    req.Alias =  new HashMap<String, String>();
	    req.ScriptAlias =  new HashMap<String, String>();
		
		readHttpdConf(req);
		
		InputStream inputstream = client.getInputStream();
		
		//InputStreamReader isr = new InputStreamReader(inputstream);
	    
	    //BufferedReader buffer = new BufferedReader(isr );
	    
	    readLine = readline(inputstream);
	    
	    int valid_request = ParseRequestLine(readLine, req);

	    if(valid_request == 1)
	    {
			req.request_headers = ParseRequestHeaders(inputstream, req.request_headers);
				
	    }
	    
	    if (req.request_headers.containsKey("content-length")) {
			//req.body = buffer.readLine();
	    	
	    	int sizebytes = Integer.parseInt(req.request_headers.get("content-length"));
	    	
	    	System.out.println(sizebytes);
	    	
	    	byte[] data = new byte[sizebytes];
	    	
	    	String bodyline = "";
	    	
	    	int bytes_read = inputstream.read(data, 0, sizebytes);
	    	
	    	if(bytes_read < 0) {
                System.out.println("Server: Tried to read from socket, read() returned < 0,  Closing socket.");
                return;
            }
	    	
	    	System.out.println("Body is "+ new String(data, 0, bytes_read));
	    	
	    	req.body = new String(data, 0, bytes_read);
	    	//inputstream.read(data);
			
	    	/*if((bodyline = buffer.readLine()) != null) {
				System.out.println(bodyline);
				req.body = bodyline;
			}*/
			//System.out.println("Body "+body);
		}
	    
	}
	
	private static String readline(InputStream inputstream) {
		
		char character; 
		String string = ""; 
		char next;
		
		try {
			do
			{
			   character = (char)inputstream.read(); 
			   
			   
			   if(character == '\r') {
				 
				   next = (char)inputstream.read();
				  
				  if(next == '\n'){
					  break; 
				  }
			   }
			   else{
				   string += character;
			   }
			}while(character != -1);
			
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return string;
	}

	protected void sendResponse( Socket client, String response ) throws IOException {
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