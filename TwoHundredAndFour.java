
public class TwoHundredAndFour extends Response{

	TwoHundredAndFour(Resource resource){
		super(resource);
		this.code = 204;
		this.reasonPhrase = "File deleted";
	}
	
	/*public void send() {
		System.out.println(this.code);
	}*/
}
