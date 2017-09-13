import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MimeTypes {

	private static Map<String, String> mime_types;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			mime_types = new HashMap<String, String>();
		    
            File mime_file = new File("src/conf/mime.types");

            BufferedReader buffer = new BufferedReader(new FileReader(mime_file));

            String readLine = "";

            //System.out.println("Reading file using Buffered Reader");

            while ((readLine = buffer.readLine()) != null) {
            	if(! readLine.isEmpty() && readLine.charAt(0)!= '#' )
            	{
            		//System.out.println(readLine);
            		
            		String extension_list = "";
            		String mime = "";
            		
            		String[] extensions = new String[0];
            		
            		if(readLine.contains("\t"))
            		{
            			mime = readLine.substring(0,readLine.indexOf("\t"));
            			
            			extension_list = readLine.substring(readLine.indexOf("\t"), readLine.length());
            			extension_list = extension_list.replace("\t", "");
            			
            			extensions = extension_list.split(" ");
            			
            			for (String extension : extensions) {
            				mime_types.put(extension,mime);
            				//System.out.println(part);
            			    //do something interesting here
            			}
            		}
            		
            		
            		//System.out.println("Contains mime "+mime);
            		//System.out.println("Contains file extension "+extensions);
            		
            		

            	}
            }
            
            /*for (String key : mimes.keySet()) {
                // ...
            	System.out.println(key);
            }*/
            
            for (Map.Entry<String, String> entry : mime_types.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                
                System.out.println("Key: "+key);
                System.out.println("Value: "+value);
                //for (String extension : values) {
    				//System.out.println(extension);
    			    //do something interesting here
    			//}
                // ...
            }
            
            
            String mime_type = mime_types.get("htm");
            System.out.println(mime_type);

            //for (String extension : extension_value) {
			//	System.out.println(extension);
			    //do something interesting here
			//}
            
            //if(mime_types.containsValue("htm"))
            	//System.out.println("exists");
            

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
