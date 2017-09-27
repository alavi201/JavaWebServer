package responsePackage;

public class TwoHundredAndOne extends Response{

    TwoHundredAndOne(Resource resource){
        super(resource);
        this.code = 201;
        this.reasonPhrase = "File created";
    }
}
