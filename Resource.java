import java.io.*;
import java.util.*;

public class Resource {

	private String absolutepath = "";
	boolean script = false;
	
	Resource(String uri, Request ws ){
		
		int first = uri.indexOf("/");
		int second = uri.indexOf("/", first + 1);
		String resource_name = "";
		
		//System.out.println(second);
		
		String checkAlias = "";
		resource_name = uri.substring(uri.lastIndexOf("/") + 1);
		
		System.out.println(resource_name);
		
		if(second != -1)
		{
			checkAlias = uri.substring(first, second +1);
		}
		
		//System.out.println(checkAlias);
		
		if(ws.Alias.containsKey(checkAlias)) {
        	this.absolutepath = ws.Alias.get(checkAlias);
        	//Print("Aliasd "+ws.uri);
        }
        else if(ws.ScriptAlias.containsKey(checkAlias))
        {
        	this.script = true;
        	this.absolutepath = ws.ScriptAlias.get(checkAlias);
        }
        else
        	this.absolutepath = ws.config.get("DocumentRoot");
		
		this.absolutepath = this.absolutepath + resource_name;
		
		//if()
        
        if(absolutepath.lastIndexOf('.') == -1)
        	this.absolutepath = this.absolutepath + ws.config.get("DirectoryIndex");
        
        //System.out.println(this.absolutepath);
        
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
