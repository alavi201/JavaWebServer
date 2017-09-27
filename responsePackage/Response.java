package responsePackage;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.text.*;

public abstract class Response {

    public int code = 0;
    public String reasonPhrase = "";
    Resource resource;
    public Map<String, String> responseHeaders;
    boolean hasBody = true;
    
    protected String getHeaders() {
        
    	String headers = "";
        
        for (Map.Entry<String, String> header : this.responseHeaders.entrySet()) {
            headers += header.getKey()+": "+header.getValue()+"\r\n";
        }
       
        return headers;
    }
    
    protected String getResponseline() {
    	
        String responseLine = "";
        responseLine += "HTTP/1.1 "+this.code+" "+this.reasonPhrase+"\r\n";
        return responseLine;
    }
    
    public Response(Resource resource){
        
    	this.responseHeaders = new HashMap<String, String>();
        this.resource = resource;
        String timeStamp = "";
        
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z");
        simpledateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
        timeStamp = simpledateformat.format(new Date());
        
        this.responseHeaders.put("Server", "Alavi");
        this.responseHeaders.put("Date", timeStamp);
        this.responseHeaders.put("Connection", "Closed");
    }
    
    public void send( Socket client) throws IOException {
        OutputStream socketOutputStream = client.getOutputStream();
        socketOutputStream.write((getResponseline() + getHeaders()+"\r\n").getBytes());
      }
        
}
