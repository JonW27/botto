import java.io.*;
//import javax.mail.*;
import java.util.*;
import java.net.InetAddress;
//import com.sun.mail.smtp.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Controller{
  public static void main(String[] args){
    String lol = "testing/chromedriver";
    System.setProperty("webdriver.chrome.driver", lol);
    WebDriver driver = new ChromeDriver();
    try{
      driver.get("https://google.com");
      System.out.println("Scaffolding worked! "+ driver.getTitle());
      driver.quit();
    }
    catch(Throwable e){
      e.printStackTrace();
    }
  }
}
