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

public class ResponseFactory{

	public Response getResponse(Request req, Resource rsrc){
		String absolpath = rsrc.absolutePath();
		
		Response response = new FiveHundred(rsrc);
		
		System.out.println(absolpath);
		
		System.out.println(req.request_method);
		
        File file = new File(absolpath);
        if(!file.exists()) {
        	 if(req.request_method.equals("PUT")){
        		//Print("Create file");
        		try{
        		    PrintWriter writer = new PrintWriter(absolpath, "UTF-8");
        		    writer.println("The first line");
        		    writer.println("The second line");
        		    writer.close();
        		    response = new TwoHundredAndOne(rsrc);
	        		//response.send();
        		} catch (IOException e) {
        			response = new FiveHundred(rsrc);
	        		//response.send();
        		   // do something
        		} 
	        		
        	}
        	else {
        		System.out.println("File not found");
        		//Print("404");
        		response = new FourHundredAndFour(rsrc);
        		//response.send();
        	}
        	// do something
        }else {
        	String line = "";
        	
        	System.out.println("File found");
        	
        	if(rsrc.isScript())
        	{
        		File script = new File(rsrc.absolutePath());

        		String[] script_config = new String[0];
        		
        		try {  
	                BufferedReader buffer = new BufferedReader(new FileReader(script));
	
	                String readLine = "";
	                String path = "";
	
	                //System.out.println("Reading file using Buffered Reader");
	
	                readLine = buffer.readLine();
	                
	                script_config = readLine.split(" ");
	                
	                path = script_config[0].replace("#!","");
	                	
	                path = "perl";
	                
	        		StringBuilder builder = new StringBuilder();
	        		String[] command = {path, script_config[1], rsrc.absolutePath()};
	        		ProcessBuilder p = new ProcessBuilder(command);

	        		Map<String, String> enviroment_variables = p.environment(); 
	        		
	        		for (Map.Entry<String, String> header : req.request_headers.entrySet()) {
	        			enviroment_variables.put(header.getKey(), header.getValue());
	        		}
	        		
			    	//p.directory(new File("C:/Perl64/bin/"));
			        // create a process builder to send a command and a argument
			        Process p2 = p.start(); 
			        p2.waitFor();
			        
			        OutputStream stdin = p2.getOutputStream(); // <- Eh?
			        InputStream stdout = p2.getInputStream();

			        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

			        writer.write(req.body);
			        
			        
			        BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			        
	
			        //log.info("Output of running " + command + " is: ");
			        //System.out.println("Output of running " + command + " is: ");
			        while ((line = br.readLine()) != null) {
			        	//System.out.println(line);
			        	builder.append(line);
			        	builder.append(System.getProperty("line.separator"));
			        }
			        
			        rsrc.output = builder.toString();
	
			        response = new TwoHundred(rsrc);
    		    } catch (Exception e) {
                e.printStackTrace();
              }
        		/*try {
                    ProcessBuilder pb = new ProcessBuilder(absolpath,req.query_params);
                    Process p = pb.start();    
                    p.waitFor();       */       
                    System.out.println("Script executed successfully");
                  /*} catch (Exception e) {
                    e.printStackTrace();
                  }*/
        	}
        	else
        	{
        		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z");
        		
	        	switch (req.request_method){
		        	case "GET":
		        		System.out.println("In GET");
		        		
		        		response = new TwoHundred(rsrc);
			        	
		        		if(req.request_headers.containsKey("if-modified-since")){
		        			
		        			String ifModifiedTime = req.request_headers.get("if-modified-since");
		        			SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss z");
		        			Date date;
							try {
								date = df.parse(ifModifiedTime);
								long epoch = date.getTime();
								System.out.println(epoch);
								if(file.lastModified() > epoch) {
									System.out.println("Need to do a 304");
									response = new ThreeHundredAndFour(rsrc);
								}
								
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		        		    
		        		}
		        			//response.responseHeaders.put("Content-Length", Long.toString(file.length()) );
		    			
		        		break;
		        	case "POST":
		        		//Print("200");
		        		System.out.println("In POST");
		        		response = new TwoHundred(rsrc);
		        						        		//Print("200");
		        		break;
		        	case "HEAD":
		        		System.out.println("In HEAD");
		        		System.out.println("Before Format : " + file.lastModified());

		        		//System.out.println("After Format : " + sdf.format(file.lastModified()));
		        		response = new TwoHundred(rsrc);
		        		response.responseHeaders.put("Last-Modified", sdf.format(file.lastModified()));
		        		response.hasBody = false;
		        		break;
		        	case "PUT":
		        		try{
		        			//Print("Overwriting");
		        			FileOutputStream stream = new FileOutputStream(rsrc.absolutePath());
		        			try {
		        			    stream.write(req.body_byte_array);
		        			} finally {
		        			    stream.close();
		        			}
		        		    response = new TwoHundredAndOne(rsrc);
			        		//response.send();
		        		} catch (IOException e) {
		        			response = new FiveHundred(rsrc);
			        		//response.send();
		        		   // do something
		        		} 
		        		break;
		        	case "DELETE":
		        		//Print("Delete File");
		        		if(file.delete()){
		        			System.out.println(file.getName() + " is deleted!");
		        			response = new TwoHundredAndFour(rsrc);
			        		//response.send();
		        		}else{
		        			System.out.println("Delete operation is failed.");
		        			response = new FiveHundred(rsrc);
			        		//response.send();
		        		}
	
		        		break;
		        		
		        }
        	}
        }

        return response;
        
      //response.send( client);
      //client.close();
	}
	
	

}
