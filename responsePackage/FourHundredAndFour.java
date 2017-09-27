package responsePackage;

public class FourHundredAndFour extends Response{

    FourHundredAndFour(Resource resource){
        super(resource);
        this.code = 404;
        this.reasonPhrase = "File not found";
    }
}
