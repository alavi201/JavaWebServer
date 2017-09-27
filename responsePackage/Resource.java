package responsePackage;
import configReaderPackage.HttpdConf;
import configReaderPackage.Htaccess;
import java.io.*;
import java.util.*;

public class Resource {

    private String absolutepath = "";
    boolean script = false;
    public String output = "";
    public String resourceName = "";
    public String pathWithoutDocRoot = "";
    public String extension = "";
    private boolean isProtected = false;
    public String accessFile = "";
    public String realm = "";
    
    public Resource(String uri, HttpdConf config ){
        
        int first = uri.indexOf("/");
        int second = uri.indexOf("/", first + 1);
        String resource_name = "";
        String absolute_path = "";
        String pathWithoutDocRoot = "";
        String checkAlias = "";
        String accessFile = (String) config.getConfig().get("AccessFileName");
        String directoryPath = "";
        boolean htaccessExists;
        
        resource_name = uri.substring(uri.lastIndexOf("/") + 1);
        
        this.resourceName = resource_name;
        
        
        if(second != -1) {
            checkAlias = uri.substring(first, second +1);
        }
        
        if(config.getAliases().containsKey(checkAlias)) {
            absolute_path = (String) config.getAliases().get(checkAlias);
        }
        else if(config.getScriptAliases().containsKey(checkAlias)) {
            this.script = true;
            absolute_path = (String) config.getScriptAliases().get(checkAlias);
        }
        else {
            absolute_path = config.getConfig().get("DocumentRoot") + checkAlias;
        }
        
        absolute_path = absolute_path + resource_name;
        
        this.pathWithoutDocRoot = absolute_path;
        
        directoryPath = absolute_path;
        
        File file = new File(absolute_path);
        
        if(!file.isFile()) {
            absolute_path = absolute_path + config.getConfig().get("DirectoryIndex");
        }
        
        this.absolutepath = absolute_path.replace("//","/");
        
        this.extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        
        htaccessExists = new File(directoryPath, accessFile).exists();
        
        if(htaccessExists) {
        	
        	Htaccess htaccess;
			
        	try {
        		
				this.isProtected = true;
	        	this.accessFile = (String) config.getConfig().get("AccessFileName");
	        	htaccess = new Htaccess(directoryPath+File.separator+this.accessFile);
	        	this.realm = (String) htaccess.getConfig().get("AuthName");
	        	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        	
        }
        
    }
    
    public String absolutePath() {
        return this.absolutepath;
    }
    
    public boolean isScript() {
        return this.script;
    }
    
    public boolean isProtected() {
        return this.isProtected;
    }
}
