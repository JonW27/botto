import java.io.*;
//import javax.mail.*;
import java.util.concurrent.TimeUnit;
import java.net.InetAddress;
//import com.sun.mail.smtp.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class Controller{
  public static String getMessage(WebDriver driver){
    int size = driver.findElements(By.className("message-group")).size();
  String str = driver.findElements(By.className("message-group")).get(size - 1).getText();
  return str.substring(str.indexOf('\n') + 1,str.length());
  }
    public static void send(WebDriver driver,String str){
      driver.findElement(By.tagName("textarea")).sendKeys(str);
      driver.findElement(By.tagName("textarea")).sendKeys(Keys.RETURN);
    }
  public static void main(String[] args){
    String lol = "testing/chromedriver.exe";
    System.setProperty("webdriver.chrome.driver", lol);
    WebDriver driver = new ChromeDriver();
    try{
      driver.get("https://discordapp.com/channels/263162147792617482/263162147792617482");
      System.out.println("Scaffolding worked! "+ driver.getTitle());
      driver.findElement(By.id("register-email")).sendKeys("botto@haxsource.tech"); //email
      driver.findElement(By.id("register-password")).sendKeys("RlenzPS6"); // password
      driver.findElement(By.id("register-password")).submit();
      TimeUnit.SECONDS.sleep(5);
      if(driver.findElements(By.className("markdown-modal-close")).size() > 0){
        driver.findElement(By.className("markdown-modal-close")).click();
      }
      if(driver.findElements(By.xpath("//*[contains(text(), 'Skip')]")).size() > 0){
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.xpath("//*[contains(text(), 'Skip')]")).click();
      }
      driver.get("https://discordapp.com/channels/263162147792617482/263162147792617482");
      System.out.println(getMessage(driver));
      while(true){
	  if(getMessage(driver).equals("hi")){
	      send(driver,"hello");
	  }
	  else if(getMessage(driver).equals("break")){
	      send(driver,"exiting loop");
	      break;
	  }
	  TimeUnit.SECONDS.sleep(1);
      }
      //System.out.println(driver.getPageSource());
      TimeUnit.SECONDS.sleep(1);
      driver.quit();
    }
    catch(Throwable e){
      e.printStackTrace();
    }
  }
}
