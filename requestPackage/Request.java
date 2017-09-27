package requestPackage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.net.*;

public class Request {

    public String uri = "";
    public String requestMethod = "";
    private String http_version = "";
    public String body = "";
    public byte[] bodyByteArray;
    public String query_params;
    public String requestLine = "";
    public boolean isValid;
    public Map<String, String> requestHeaders;
    public Map<String, String> config;
    public Map<String, String> Alias;
    public Map<String, String> ScriptAlias;
    
    public static boolean ParseRequestLine(String requestLine, Request request)
    {
        request.isValid = true;
        
        try {
                    
            if(requestLine.isEmpty()) {
                request.isValid = false;
            }
            
            request.requestLine = requestLine;
            
            String requestParts[] = requestLine.split(" ");
            
            if(requestParts.length != 3) {
                request.isValid = false;
            }
            
            request.requestMethod = requestParts[0];
            request.uri = requestParts[1];
            request.http_version = requestParts[2].substring(requestParts[2].indexOf("/") + 1, requestParts[2].length());
            
            if(request.uri.contains("?")) {
                request.uri = request.uri.substring(0, request.uri.indexOf("?"));
                String query_string = request.uri.substring(request.uri.indexOf("?") + 1, request.uri.length());
                request.query_params = query_string;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return request.isValid;
    }
    
    public static Map<String, String> ParseRequestHeaders(InputStream inputstream, Map<String, String> requestHeaders)
    {
        String readLine = "" ;
        
        try {
                    
            while ((readLine = readline(inputstream)) != null) {
                //System.out.println(readLine);
                
                if(readLine.equals(""))
                {
                    break;
                }
                
                String value = "";
                String key = "";
                //System.out.println("Done headers");
                //else
                //{
                String header_parts[] = readLine.split(": ");
                key = header_parts[0].toLowerCase();
                
                //System.out.println(key);
                //System.out.println(header_parts[1]);
                
                if(key.equals("authorization")) {
                    //System.out.println(header_parts[1]);
                    String value_parts[] = header_parts[1].split(" ");
                    value = value_parts[1];
                    //System.out.println(value);
                }
                else
                    value = header_parts[1];
                requestHeaders.put(key, value);
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return requestHeaders;
        
    }

    public void readRequest( Socket client, Request request ) throws IOException {
        String readLine;
        String response = "200";
        
        request.requestHeaders = new HashMap<String, String>();
        request.query_params = "";
        request.config =  new HashMap<String, String>();
        request.Alias =  new HashMap<String, String>();
        request.ScriptAlias =  new HashMap<String, String>();
        
        InputStream inputstream = client.getInputStream();
        
        //InputStreamReader isr = new InputStreamReader(inputstream);
        
        //BufferedReader buffer = new BufferedReader(isr );
        
        readLine = readline(inputstream);
        
        ParseRequestLine(readLine, request);

        if(request.isValid)
        {
            request.requestHeaders = ParseRequestHeaders(inputstream, request.requestHeaders);
                
        }
        
        if (request.requestHeaders.containsKey("content-length")) {
            
            int sizebytes = Integer.parseInt(request.requestHeaders.get("content-length"));
            
            this.bodyByteArray = new byte[sizebytes];
            
            int bytes_read = inputstream.read(this.bodyByteArray, 0, sizebytes);
            
            request.body = new String(this.bodyByteArray, 0, bytes_read);
        }
    }
    
    private static String readline(InputStream inputstream) {
        
        char character; 
        String string = ""; 
        char next;
        
        try {
            do {
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
            } while(character != -1);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return string;
    }
}