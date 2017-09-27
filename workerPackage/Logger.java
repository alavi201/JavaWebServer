package workerPackage;
import java.util.*;
import java.util.logging.Level;

import requestPackage.Request;
import responsePackage.Response;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.io.*;
import java.net.Socket;

public class Logger {
    
    private File file ;
    
    Logger(String filename){
        
        try {
            this.file = new File(filename);
        
            if(!file.exists()) {
                file.getParentFile().mkdir();
                file.createNewFile();
            }
            
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void write(Request request, Response response, Socket client){
        
        try{
            
            String username = "";
            String logLine = "";
            
            logLine += client.getInetAddress().toString()+" ";
            
            if(request.requestHeaders.containsKey("authorization")) {
                username = getUsername(request.requestHeaders.get("authorization"));
            }
            else {    
                username = "-";        
            }
            
            logLine += username+" ";
            
            String timeStamp = new SimpleDateFormat("[d/MMM/yyyy:hh:mm:ss Z]").format(new Date());
            
            logLine += timeStamp+" ";
            
            logLine += request.requestLine+" ";
            
            logLine += response.code+" ";
            
            if(response.responseHeaders.containsKey("Content-Length")) {
                logLine += response.responseHeaders.get("Content-Length");
            } else {
                logLine += "-";
            }
            
            BufferedWriter output = new BufferedWriter(new FileWriter(this.file.getAbsolutePath(), true));
            
            System.out.println(logLine);
            
            output.write(logLine);
            output.newLine();
            output.flush();
            output.close();
            
        } catch (IOException e) {
        	e.printStackTrace();
        } 
    }
    
    
    public String getUsername(String authheader) {
        
        String credentials = new String(
          Base64.getDecoder().decode( authheader ),
          Charset.forName( "UTF-8" )
        );

        // The string is the key:value pair username:password
        String[] tokens = credentials.split( ":" );
        
        return tokens[0];
        
    }

}
