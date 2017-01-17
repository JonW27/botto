import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.util.*;
import java.io.File;
public class OV{
	public static void main(String args[]){
		String string = "import their.extension.blab;\n";
		try{
			File file = new File("OV.java");
			List fix = FileUtils.readLines(file, "UTF-8");
			for(int i = 0; i < fix.size(); i++){
				string = string + fix.get(i) + "\n";
			}
			FileUtils.writeStringToFile(file, string, "UTF-8");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
