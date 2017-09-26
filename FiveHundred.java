
public class FiveHundred extends Response{

    FiveHundred(Resource resource){
        super(resource);
        this.code = 500;
        this.reasonPhrase = "Internal Server Error";
    }
}