
public class FiveHundred extends Response{

	FiveHundred(Resource resource){
		super(resource);
		this.code = 500;
		this.reasonPhrase = "InternalServerError";
	}
	
	/*public void send() {
		System.out.println(this.code);
	}*/
}
