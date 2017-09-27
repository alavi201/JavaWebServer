import java.io.*;
import java.util.*;

import configReaderPackage.HttpdConf;
import configReaderPackage.MimeTypes;
import workerPackage.Worker;

import java.net.*;

public class WebServer {
    
    HttpdConf configuration;
    MimeTypes mimeTypes;
    ServerSocket socket;
    
    public static void main(String[] args) {
        
        WebServer webserver = new WebServer();
        webserver.configuration = new HttpdConf("conf/httpd.conf");
        webserver.mimeTypes = new MimeTypes("conf/mime.types");
        webserver.start();
    }
    
    public void start(){
        
        try {    
            
            ServerSocket socket = new ServerSocket(Integer.parseInt((String)this.configuration.getConfig().get("Listen")));
            Socket client = null;

            while( true ) {
                  client = socket.accept();
                  Worker worker = new Worker(client, this.mimeTypes, this.configuration);
                  Thread serverThread = new Thread(worker);
                  serverThread.start();                    
                }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
