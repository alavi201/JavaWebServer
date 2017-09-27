package responsePackage;

public class TwoHundredAndFour extends Response{

    TwoHundredAndFour(Resource resource){
        super(resource);
        this.code = 204;
        this.reasonPhrase = "File deleted";
    }
}
