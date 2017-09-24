import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class FourHundredAndFour extends Response{

	FourHundredAndFour(Resource resource){
		super(resource);
		this.code = 404;
		this.reasonPhrase = "File not found";
	}
	
	/*public void send() {
		System.out.println(this.code);
	}*/
}
