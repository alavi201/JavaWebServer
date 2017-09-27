package responsePackage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileInputStream;

public class TwoHundred extends Response{

    String content_length = "";
    
    public TwoHundred(Resource resource){
        super(resource);
        this.code = 200;
        this.reasonPhrase = "OK";
    }
    
    public void send(Socket client) throws IOException{
        
        OutputStream socketOutputStream = client.getOutputStream();
        
        if(this.resource.isScript()) {    
        	socketOutputStream.write((getResponseline()+this.resource.output).getBytes());
        
        } else {
            
        	File file = new File(this.resource.absolutePath());
            
            this.responseHeaders.put("Content-Type", resource.extension);
            this.responseHeaders.put("Content-Length", Long.toString(file.length()) );
            
            socketOutputStream.write((getResponseline() + getHeaders()+"\r\n").getBytes());
            
            if(this.hasBody)
            {
                FileInputStream inputstream = new FileInputStream(file);
                byte fileContent[] = new byte[(int)file.length()];
                
                inputstream.read(fileContent);
                socketOutputStream.write(fileContent);
            }
            
        }
    }
}
