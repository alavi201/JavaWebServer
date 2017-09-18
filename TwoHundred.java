import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TwoHundred extends Response{

	TwoHundred(){
		this.code = 200;
		this.reasonPhrase = "OK";
	}
	
	/*public void send() {
		System.out.println(this.code);
	}*/
	
	
}
