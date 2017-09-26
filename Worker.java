import java.io.*;
import java.util.*;
import java.net.*;

public class Worker implements Runnable {

    private Socket client;
    private MimeTypes mimeTypes;
    private HttpdConf configuration;

    Worker(Socket client, MimeTypes mimeTypes, HttpdConf configuration) {
        this.client = client;
        this.configuration = configuration;
        this.mimeTypes = mimeTypes;
    }

    @Override
    public void run() {
        
    	ResponseFactory responseFactory = new ResponseFactory();
        Request request = new Request();

        Logger log = new Logger((String) this.configuration.getConfig().get("LogFile"));

        try {
            request.readRequest(client, request);
            Resource resource = new Resource(request.uri, this.configuration);
            Response response = responseFactory.getResponse(request, resource);

            response.send(client);

            log.write(request, response, this.client);
            
            client.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
