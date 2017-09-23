import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;

public class TwoHundred extends Response{

	String content_length = "";
	
	public TwoHundred(Resource resource){
		super(resource);
		this.code = 200;
		this.reasonPhrase = "OK";
		
		setLength("50");
	}
	
	public void send(Socket client) throws IOException{
		
		String response = "";
	    PrintWriter out = new PrintWriter( client.getOutputStream(), true );
	    OutputStream socketOutputStream = client.getOutputStream();
		

	    //int gift = (int) Math.ceil( Math.random() * 100 );
	    //String response = "404 Gee, thanks, this is for you: " + gift;
	    
	    response = this.code+" "+this.reasonPhrase;
	    
	    if(this.resource.isScript()){
	    	
	    	File file = new File("D:/Users/Ali/workspace/server/public_html/output.html");
	    	
	    	socketOutputStream.write(("HTTP/1.1 "+response+"\r\nContent-Length: 3020\r\n"+this.resource.output).getBytes());
	    }else {
		    File file = new File(this.resource.absolutePath());
			FileInputStream fin = null;
			
			fin = new FileInputStream(file);
	
			byte fileContent[] = new byte[(int)file.length()];
			
			// Reads up to certain bytes of data from this input stream into an array of bytes.
			fin.read(fileContent);
			//create string from byte array
			String s = new String(fileContent);
			//System.out.println("File content: " + s);
			
			socketOutputStream.write(("HTTP/1.1 "+response+"\r\n"+defaultHeaders()+"Content-Length: "+(int)file.length()+"\r\nContent-Type: text/HTML\r\nConnection: close\r\n\r\n").getBytes());
		    socketOutputStream.write(fileContent);
		    //out.println("HTTP/1.1 200 OK\\r\\nCache-Control: no-cache, private\\r\\nContent-Length: 107\\r\\nDate: Mon, 24 Nov 2014 10:21:21 GMT\\r\\n\\r\\n");
	    }
	}
	
public void send(Socket client, String output) throws IOException{
		
		String response = "";
	    PrintWriter out = new PrintWriter( client.getOutputStream(), true );
	    //int gift = (int) Math.ceil( Math.random() * 100 );
	    //String response = "404 Gee, thanks, this is for you: " + gift;
	    
	    response = this.code+" "+this.reasonPhrase;
	    
	    
		 OutputStream socketOutputStream = client.getOutputStream();
		

		socketOutputStream.write(("HTTP/1.1 "+response+"\r\n"+output).getBytes());
	    //socketOutputStream.write(fileContent);
	    //out.println("HTTP/1.1 200 OK\\r\\nCache-Control: no-cache, private\\r\\nContent-Length: 107\\r\\nDate: Mon, 24 Nov 2014 10:21:21 GMT\\r\\n\\r\\n");

	}
	
	public void setLength(String length) {
		this.content_length = length;
	}
	
	
}
