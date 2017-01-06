import java.util.*;
import java.io.*;
//import javax.mail.*;
import java.util.concurrent.TimeUnit;
import java.net.InetAddress;
//import com.sun.mail.smtp.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

class info{
    public static String chromePath = "testing/chromedriver";
}
public class Botto{
    private static ArrayList<Controller> Controllers = new ArrayList<WebDriver>();
    private static Controller getController(int index){
	return Controllers.get(index);
    }
    private static Controller makeController(String tag){
	WebDriver driver = new Chromedriver();
	Controller discord = new Discord(driver,"discord");
	Controllers.add(discord);
    }
    private static int tickLength = 1;
    private static int getTickLength(){
	return ticklength;
    }
    private static int getControllerIndex(String tag){
    	for(int i = 0;i < Controllers.size();i++){
    	    if(getController(i).getTag().equals(tag)){
    		return i;
    	    }
   	}
	return -1;
    }
    public static void main(String[] args){
	System.setProperty("webdriver.chrome.driver", info.chromePath);
	While(true){
	    for(int i = 0;i < Controllers.size();i++){
		getController(i).tick();
	    }
	    TimeUnit.SECONDS.sleep(tickLength); // add this to while loop
	}
  }
}
