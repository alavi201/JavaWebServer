import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreeHundredAndFour extends Response{

	ThreeHundredAndFour(Resource resource){
		super(resource);
		this.code = 304;
		this.reasonPhrase = "Not Modified";
	}
	
	protected void send( Socket client) throws IOException {
		OutputStream socketOutputStream = client.getOutputStream();
		socketOutputStream.write((getResponseline() + getHeaders()+"\r\n").getBytes());
	  }
}
