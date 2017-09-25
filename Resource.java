import java.io.*;
import java.util.*;

public class Resource {

	private String absolutepath = "";
	boolean script = false;
	public String output = "";
	public String resourceName = "";
	public String pathWithoutDocRoot = "";
	
	Resource(String uri, HttpdConf config ){
		
		int first = uri.indexOf("/");
		int second = uri.indexOf("/", first + 1);
		String resource_name = "";
		String absolute_path = "";
		String pathWithoutDocRoot = "";
		
		//System.out.println(second);
		
		String checkAlias = "";
		resource_name = uri.substring(uri.lastIndexOf("/") + 1);
		
		this.resourceName = resource_name;
		
		//System.out.println("Resource Name "+resource_name);
		
		if(second != -1)
		{
			checkAlias = uri.substring(first, second +1);
		}
		
		//System.out.println("CheckALias "+checkAlias);
		
		if(config.getAliases().containsKey(checkAlias)) {
			absolute_path = (String) config.getAliases().get(checkAlias);
        	//checkAlias = "";
        	//Print("Aliasd "+config.uri);
        }
        else if(config.getScriptAliases().containsKey(checkAlias))
        {
        	this.script = true;
        	absolute_path = (String) config.getScriptAliases().get(checkAlias);
        	
        }
        else
        	absolute_path = config.getConfig().get("DocumentRoot") + checkAlias;
		
		absolute_path = absolute_path + resource_name;
		
		this.pathWithoutDocRoot = absolute_path;
		
		//System.out.println("Absoulte path "+absolute_path);
		
		//if()
		File file = new File(absolute_path);
		
        
        if(!file.isFile())
        	absolute_path = absolute_path + config.getConfig().get("DirectoryIndex");
        
        this.absolutepath = absolute_path.replace("//","/");
        
        //System.out.println(this.absolutepath);
        
        //Print(config.uri);
	}
	
	public String absolutePath() {
		
		return this.absolutepath;
		
	}
	
	public boolean isScript() {
		return this.script;
	}
	
	public boolean isProtected() {
		return false;
	}
		//+ absolutePath() : String
		//+ isScript() : boolean
		//+ isProtected() : boolean
}
