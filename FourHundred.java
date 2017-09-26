
public class FourHundred extends Response{

    FourHundred(Resource resource){
        super(resource);
        this.code = 400;
        this.reasonPhrase = "Bad Request";
    }
}
