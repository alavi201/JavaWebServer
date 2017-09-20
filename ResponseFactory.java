import java.io.BufferedReader;
import java.io.FileReader;

public class ResponseFactory{

	public Response getResponse(String request){
		switch (request){
    	case "200":
    		return new TwoHundred();
    	case "201":
    		return new TwoHundredAndOne();
    	case "204":
    		return new TwoHundredAndFour();
    	case "401":
    		return new FourHundredAndOne();  
    	case "404":
    		return new FourHundredAndFour();
    	case "500":
    		return new FiveHundred();
		}
		
		return new FiveHundred();
	}
	
	

}
