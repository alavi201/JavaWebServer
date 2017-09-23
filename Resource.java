import java.io.*;
import java.util.*;

public class Resource {

	private String absolutepath = "";
	boolean script = false;
	public String output = "";
	
	Resource(String uri, Request ws ){
		
		int first = uri.indexOf("/");
		int second = uri.indexOf("/", first + 1);
		String resource_name = "";
		String aboslute_path = "";
		
		//System.out.println(second);
		
		String checkAlias = "";
		resource_name = uri.substring(uri.lastIndexOf("/") + 1);
		
		System.out.println("Resource Name"+resource_name);
		
		if(second != -1)
		{
			checkAlias = uri.substring(first, second +1);
		}
		
		System.out.println(checkAlias);
		
		if(ws.Alias.containsKey(checkAlias)) {
			aboslute_path = ws.Alias.get(checkAlias);
        	//checkAlias = "";
        	//Print("Aliasd "+ws.uri);
        }
        else if(ws.ScriptAlias.containsKey(checkAlias))
        {
        	this.script = true;
        	aboslute_path = ws.ScriptAlias.get(checkAlias);
        	
        }
        else
        	aboslute_path = ws.config.get("DocumentRoot") + checkAlias;
		
		aboslute_path = aboslute_path + resource_name;
		
		//System.out.println("Absoulte path "+this.absolutepath);
		
		//if()
		File file = new File(aboslute_path);
		
        
        if(!file.isFile())
        	aboslute_path = aboslute_path + ws.config.get("DirectoryIndex");
        
        this.absolutepath = aboslute_path.replace("//","/");
        
        System.out.println(this.absolutepath);
        
        //Print(ws.uri);
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
