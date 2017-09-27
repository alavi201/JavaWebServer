package workerPackage;
import java.io.*;
import java.util.*;

import configReaderPackage.HttpdConf;
import configReaderPackage.MimeTypes;
import requestPackage.Request;
import responsePackage.Resource;
import responsePackage.Response;
import responsePackage.ResponseFactory;

import java.net.*;

public class Worker implements Runnable {

    private Socket client;
    private MimeTypes mimeTypes;
    private HttpdConf configuration;

    public Worker(Socket client, MimeTypes mimeTypes, HttpdConf configuration) {
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
