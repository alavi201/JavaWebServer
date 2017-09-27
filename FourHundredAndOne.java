import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class FourHundredAndOne extends Response{

    FourHundredAndOne(Resource resource){
        super(resource);
        this.code = 401;
        this.reasonPhrase = "Not Authorized";
    }
    
    public void send(Socket client) throws IOException{
        
        String response = "";
        PrintWriter out = new PrintWriter( client.getOutputStream(), true );
        OutputStream socketOutputStream = client.getOutputStream();
        
        this.responseHeaders.put("WWW-Authenticate","Basic realm=\" "+this.resource.realm+" \" ");
            
        socketOutputStream.write((getResponseline() + getHeaders()+"\r\n").getBytes());
    }
}
