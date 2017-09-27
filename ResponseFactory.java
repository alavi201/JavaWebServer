import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class ResponseFactory{

    public Response getResponse(Request request, Resource resource){
        
        HttpdConf httpd = new HttpdConf("conf/httpd.conf");
        String absolpath = resource.absolutePath();
        File file = new File(absolpath);
        String directoryPath = file.getAbsoluteFile().getParentFile().getAbsolutePath();
        
        if(!request.isValid) {
            
        	Response response = new FourHundred(resource);
            return response;
        }
        
        // in case of any exceptions, return a 500 response
        Response response = new FiveHundred(resource);    
        
        if(resource.isProtected()) {
            try {
                
                Htaccess htaccess = new Htaccess(directoryPath+File.separator+resource.accessFile);
                
                Htpassword htpassword = new Htpassword(htaccess.config.get("AuthUserFile"));
                
                if(request.requestHeaders.containsKey("authorization")){
                    
                	String authToken = request.requestHeaders.get("authorization");
                   
                    boolean isAuthorized = htpassword.isAuthorized(authToken);
                    
                    if(!isAuthorized) {
                        response = new FourHundredAndThree(resource);
                        return response;
                    }
                }
                else {
                    response = new FourHundredAndOne(resource);
                    return response;
                
                }
            } catch (IOException e1) {
            	return response;
            }
        }
        
        if(!file.exists()) {
            
        	if(request.requestMethod.equals("PUT")) {
                
        		try{
                       File newFile = new File(resource.pathWithoutDocRoot);
                       newFile.getParentFile().mkdir();
                       newFile.createNewFile();
                        
                       FileOutputStream stream = new FileOutputStream(resource.pathWithoutDocRoot);
                       
                       try {
                           stream.write(request.bodyByteArray);
                       } finally {
                           stream.close();
                       }
                        
                       response = new TwoHundredAndOne(resource);
                       
               } catch (IOException e) {
            	   return response;
               } 
                        
           } else {
               response = new FourHundredAndFour(resource);
               return response;
           }
        	
       } else {
           
    	   String processOutputLine = "";
                
           if(resource.isScript()) {
               
        	   File script = new File(resource.absolutePath());
    
               String[] scriptPath = new String[0];
                    
               try {  
                   BufferedReader buffer = new BufferedReader(new FileReader(script));
        
                   StringBuilder stringbuilder = new StringBuilder();
                   String readLine = "";
                   String path = "";
                   
                   readLine = buffer.readLine();
                        
                   scriptPath = readLine.split(" ");
                        
                   path = scriptPath[0].replace("#!","");
                            
                   String[] command = {path, scriptPath[1], resource.absolutePath()};
                   
                   ProcessBuilder processbuilder = new ProcessBuilder(command);
    
                   Map<String, String> environmentVariables = processbuilder.environment(); 
                        
                   environmentVariables.put("SERVER_PROTOCOL", "HTTP");
                        
                        
                   for (Map.Entry<String, String> header : request.requestHeaders.entrySet()) {
                       environmentVariables.put("HTTP_"+header.getKey(), header.getValue());
                   }
                        
                   if(!request.query_params.equals("")) {
                       environmentVariables.put("QUERY_STRING", request.query_params);
                   }
                        
                   Process process = processbuilder.start(); 
                   process.waitFor();
                        
                   OutputStream stdin = process.getOutputStream(); 
                   
                   BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
                   writer.write(request.body);
                         
                   BufferedReader bufferreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        
                   while ((processOutputLine = bufferreader.readLine()) != null) {
                	   stringbuilder.append(processOutputLine);
                       stringbuilder.append(System.getProperty("line.separator"));
                   }
                        
                   resource.output = stringbuilder.toString();
        
                   response = new TwoHundred(resource);
               } catch (Exception e) {
                    return response;
               }
               
            } else {
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z");
                simpledateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    
                switch (request.requestMethod) {
                    
                    case "GET":
                    
                        response = new TwoHundred(resource);
                            
                        if(request.requestHeaders.containsKey("if-modified-since")) {
                                
                            String ifModifiedTime = request.requestHeaders.get("if-modified-since");
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss z");
                            Date date;
                            
                            try {
                                date = sdf.parse(ifModifiedTime);
                                    
                                long epoch = date.getTime();
                                    
                                if(file.lastModified() > epoch) {
                                    response = new ThreeHundredAndFour(resource);
                                }
                                    
                            } catch (ParseException e) {
                            	response = new FiveHundred(resource);
                            }      
                        }
                        
                        break;
                    
                    case "POST":
                        
                    	response = new TwoHundred(resource);
                        
                    	break;
                    
                    case "HEAD":
                            
                        response = new TwoHundred(resource);
                        response.responseHeaders.put("Last-Modified", simpledateformat.format(file.lastModified()));
                        response.hasBody = false;
                            
                        break;
                   
                    case "PUT":
                        
                    	try{
                            FileOutputStream stream = new FileOutputStream(resource.absolutePath());
                                try {
                                    stream.write(request.bodyByteArray);
                                } finally {
                                    stream.close();
                                }
                                response = new TwoHundredAndOne(resource);
                                
                            } catch (IOException e) {
                                response = new FiveHundred(resource);
                                return response;
                            } 
                            
                        break;
                        
                    case "DELETE":
                        
                    	if(file.delete()){
                            response = new TwoHundredAndFour(resource);
                            
                        }else{
                            response = new FiveHundred(resource);
                            
                        }
        
                        break;           
                }
            }
        }    

        return response;
    }
    
}
