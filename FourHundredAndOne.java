
public class FourHundredAndOne extends Response{

	FourHundredAndOne(Resource resource){
		super(resource);
		this.code = 401;
		this.reasonPhrase = "File created";
	}
	
	/*public void send() {
		System.out.println(this.code);
	}*/
}
