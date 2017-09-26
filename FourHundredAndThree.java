
public class FourHundredAndThree extends Response{

    FourHundredAndThree(Resource resource){
        super(resource);
        this.code = 403;
        this.reasonPhrase = "Forbidden";
    }
}