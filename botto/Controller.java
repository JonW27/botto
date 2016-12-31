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

public class Controller{
  public static WebElement getMessageGroup(WebDriver driver){
    List<WebElement> messages = driver.findElements(By.className("message-group"));
    int size = messages.size();
    return messages.get(size - 1);
  }
    public static String profilePicCheck(WebElement message, String x){
     String url = message.findElement(By.className("avatar-" + x)).getAttribute("style");
     return url.substring(url.indexOf('"') + 1 ,url.lastIndexOf('"'));
    }
    public static String profilePicCheck(WebElement message){
	return profilePicCheck(message,"large");
    }
    public static List<WebElement> getMarkups(WebElement message){
        return message.findElements(By.className("markup"));
    }
    public static String getDiscriminator(WebDriver driver,WebElement message,String x){
	WebElement avatar = message.findElement(By.className("avatar-" + x));
	avatar.click();
	String discriminator = driver.findElement(By.className("user-popout"))
	    .findElement(By.className("discriminator"))
	    .getText();
	avatar.click();
	return discriminator;
    }
    public static String getDiscriminator(WebDriver driver,WebElement message){
	return getDiscriminator(driver,message,"large");
    }
    public static String getMarkup(WebElement message){
	int size = getMarkups(message).size();
	return getMarkups(message).get(size - 1).getText();
    }
    //getUsername is not a valid way of identification
    public static String getUsername(WebElement messageGroup){
	return messageGroup.findElement(By.className("user-name")).getText();
    }
    public static String getTimeStamp(WebElement messageGroup){
	return messageGroup.findElement(By.className("timestamp")).getText();
    }
    public static boolean send(WebDriver driver,String str){
      driver.findElement(By.tagName("textarea")).sendKeys(str);
      driver.findElement(By.tagName("textarea")).sendKeys(Keys.RETURN);
      return true;
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
      //System.out.println(getMessageGroup(driver));

     
      WebElement account = driver.findElement(By.className("account"));
      String username = account.findElement(By.className("username")).getText();
      String profilePic = profilePicCheck(account,"small");
      String discriminator = account.findElement(By.className("discriminator")).getText();
      int maxSleepTime = 5;
      int minSleepTime = 1;
      int sleepTime = 1;
      int maxCounter = 300;
      int counter = 300;
      while(true){
	  WebElement message = getMessageGroup(driver);
	  String markup = getMarkup(message);
	  if(!(getUsername(message).equals(username) &&
	       profilePicCheck(message).equals(profilePic))){
	      if(markup.equals("hi")){
		  send(driver,"hello " + getUsername(message));
	      }
	      else if(markup.equals("-time")){
		  send(driver,getTimeStamp(message));
	      }
	      else if(markup.equals("say hi")){
		  send(driver,"hi");
	      }
	      else if(markup.equals("-profilePicCheck")){
		  send(driver,"I am");
		  send(driver,username);
		  send(driver,profilePic);
		  send(driver,"message from");
		  send(driver,profilePicCheck(message));
	      }
	      else if(markup.equals("-getDiscriminator")){
		  send(driver,getDiscriminator(driver,message));
	      }
	      else if(markup.equals("break")){
		  send(driver,"exiting loop");
		  break;
	      }
	      counter = maxCounter;
	  }
	  else{
	      if(counter == maxCounter){
		  sleepTime = minSleepTime;
	      }
	      else if(counter > 0){
		  counter -= 1;
	      }
	      else if(counter == 0){
		  sleepTime = maxSleepTime;
		  counter -= 1;
	      }
	  }
	  TimeUnit.SECONDS.sleep(sleepTime);
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
