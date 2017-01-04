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
    private static void addController(Controller x){
	Controllers.add(x);
    }
    private static Controller getController(int index){
	return Controllers.get(index);
    }
    public static void main(String[] args){
	System.setProperty("webdriver.chrome.driver", info.chromePath);
	WebDriver driver = new Chromedriver();
	Controller discord = new Discord(driver,"discord");
	Controllers.add(discord);
	TimeUnit.SECONDS.sleep(1); // add this to while loop
  }
}
