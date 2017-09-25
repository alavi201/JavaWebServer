import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File file = new File("D:/Users/Ali/Workspace/Server/public_html/ali.text");
		
        
        if(!file.isFile())
        	System.out.println("Not a file");
        
        Path file_path = Paths.get("D:/Users/Ali/Workspace/Server/public_html/ali.text");
        //boolean isRegularExecutableFile = Files.isRegularFile(file) &
          //       Files.isReadable(file) & Files.isExecutable(file);
        System.out.println( System.getProperty("os.name").toLowerCase().contains("windows") );
	}

}
