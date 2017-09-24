
public class TwoHundredAndOne extends Response{

	TwoHundredAndOne(Resource resource){
		super(resource);
		this.code = 201;
		this.reasonPhrase = "File created";
	}
	
	/*public void send() {
		System.out.println(this.code);
	}*/
}
