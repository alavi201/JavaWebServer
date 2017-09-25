import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MimeTypes extends ConfigurationReader {

	private static Map<String, String> types;

	MimeTypes(String filename){
		super(filename);
		types = new HashMap<String, String>();
		this.load();
	}
	
	public void load() {

		try {			
			
            while (this.hasMoreLines()) {
          	
            	String Line = this.nextLine();
            	
            	//System.out.println(Line);
            	
            	if(! Line.isEmpty() && Line.charAt(0)!= '#' )
            	{	
            		String extension_list = "";
            		String mime = "";
            		
            		String[] extensions = new String[0];
            		
            		if(Line.contains("\t"))
            		{
            			mime = Line.substring(0,Line.indexOf("\t"));
            			
            			//System.out.println("Contains mime "+mime);
                		
            			extension_list = Line.substring(Line.indexOf("\t"), Line.length());
            			extension_list = extension_list.replace("\t", "");
            			extensions = extension_list.split(" ");
            			
            			for (String extension : extensions) {
            				//System.out.println("Contains file extension "+extension);
                			
            				 this.types.put(extension,mime);
            			}
            		}
            		
            	}
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	
	public String lookup(String extension) throws IOException{
        if(this.types.containsKey(extension)){
            return this.types.get(extension);
        }else{
            return "text/text";
        }
   }
	
	public static void main(String[] args) throws IOException {
	
		MimeTypes mimes = new MimeTypes("src/conf/mime.types");
		
		for( String key : mimes.types.keySet()){
            System.out.println(key + ": " + mimes.types.get(key));
        }
		
	}
	
}
