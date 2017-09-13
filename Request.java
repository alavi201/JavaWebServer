import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {

	private String uri = "";
	private String request_method = "";
	private String http_version = "";
	private Map<String, String> request_headers;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String request_line = "";
		File http_file = new File("src/http_request.txt");
		
		Request ws = new Request();
		
		try {
	        BufferedReader buffer = new BufferedReader(new FileReader(http_file));
	        String body = "" ;
	       
	        request_line = buffer.readLine();
	        
	        int valid_request = ParseRequestLine(request_line, ws);
	        
	        System.out.println(ws.uri);
	        System.out.println(ws.http_version);
			
			if(valid_request == 1)
			{
				ws.request_headers = new HashMap<String, String>();
				
				ws.request_headers = ParseRequestHeaders(buffer, ws.request_headers);
				
				/*for (Map.Entry<String, String> entry : ws.request_headers.entrySet()) {
	                String key = entry.getKey();
	                String value = entry.getValue();
	                
	                System.out.println("Key: "+key+", Value: "+value);
	                
	                // ...
	            }*/
				
				if (ws.request_headers.get("Content-Length") != null) {
					body = buffer.readLine();
					System.out.println("Body "+body);
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
	        	return_value = 1;
	        	//System.out.println("400");
	        
	        String request_parts[] = request_line.split(" ");
	        
	        if(request_parts.length != 3)
	        	return_value = 1;
	        	//buffer.System.out.println("400");
	        
	        ws.request_method = request_parts[0];
			ws.uri = request_parts[1];
			ws.http_version = request_parts[2].substring(request_parts[2].indexOf("/") + 1, request_parts[2].length());
			
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
				System.out.println(readLine);
				
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


}