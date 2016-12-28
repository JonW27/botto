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
  public static void main(String[] args){
    String lol = "testing/chromedriver";
    String discordChannel = "https://discordapp.com/channels/263162147792617482/263162147792617482";
    System.setProperty("webdriver.chrome.driver", lol);
    WebDriver driver = new ChromeDriver();
    try{
      driver.get(discordChannel);
      System.out.println("Scaffolding worked! "+ driver.getTitle());
      driver.findElement(By.id("register-email")).sendKeys(""); //email
      driver.findElement(By.id("register-password")).sendKeys(""); // password
      driver.findElement(By.id("register-password")).submit();
      TimeUnit.SECONDS.sleep(12);
      if(driver.findElements(By.className("markdown-modal-close")).size() > 0){
        driver.findElement(By.className("markdown-modal-close")).click();
      }
      if(driver.findElements(By.xpath("//*[contains(text(), 'Skip')]")).size() > 0){
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.xpath("//*[contains(text(), 'Skip')]")).click();
      }
      driver.get(discordChannel);
      driver.findElement(By.tagName("textarea")).sendKeys("my test worked... part 1 complete :D");
      driver.findElement(By.tagName("textarea")).sendKeys(Keys.RETURN);
      //System.out.println(driver.getPageSource());
      TimeUnit.SECONDS.sleep(1);
      driver.quit();
    }
    catch(Throwable e){
      e.printStackTrace();
    }
  }
}
