import java.util.*;
import java.io.*;
//import javax.mail.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import java.net.InetAddress;
//import com.sun.mail.smtp.*;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Controller{
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
  static int maxSleepTime = 5;
  static int minSleepTime = 1;
  static int sleepTime = 1;
  static int maxCounter = 300;
  static int counter = 300;
  public static void updateSleepCounter(WebDriver driver,boolean x){
  if(x){
      counter = maxCounter;
  }
  else{
      if(counter == maxCounter){
        //send(driver,"checking messages every second");
        sleepTime = minSleepTime;
      }
      if(counter > 0){
        counter -= 1;
      }
      else if(counter == 0){
        //send(driver,"counter is equal to 0, only checking messages every 5 seconds");
        sleepTime = maxSleepTime;
        counter -= 1;
      }
  }
  }
  public static String getMessage(WebDriver driver){
    int size = driver.findElements(By.className("message")).size();
    String str = driver.findElements(By.className("message")).get(size - 1).getText();
    return str.substring(str.indexOf('\n') + 1,str.length());
  }
  public static void welcome(){
    System.out.println("\n                                Welcome to "+ ANSI_CYAN + "botto"+ANSI_RESET+"!\n\nbotto is an"+ANSI_PURPLE+" easy to set up framework"+ANSI_RESET+" that allows you to "+ANSI_YELLOW+"turn your device into an instant IoT device.\n\nbotto supports channels such as discord, fb messenger, and slack,"+ANSI_RESET+"to let "+ANSI_GREEN+"you make your own programmable recipes.\n\nProgram Usage:"+ANSI_PURPLE+"\njava Controller [option]\n\n"+ANSI_GREEN+"Options include:"+ANSI_PURPLE+"\ndiscord\nmessenger\nslack\n"+ANSI_RESET);
    Model.checkForSettings();
  }

  public static WebElement getMessageGroup(WebDriver driver){
    List<WebElement> messages = driver.findElements(By.className("message-group"));
    int size = messages.size();
    return messages.get(size - 1);
  }
  public static String profilePicCheck(WebElement message, String x){
      String url = message.findElement(By.className("avatar-" + x)).getAttribute("style");
      return url.substring(url.indexOf(' ') + 1 ,url.indexOf(')'));
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
      driver.findElement(By.tagName("textarea")).sendKeys(Keys.ENTER); //KEYS.RETURN for chromedriver
      File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      System.out.println(srcFile);
      try{
        FileUtils.copyFile(srcFile, new File("screen.png"));
      }
      catch(Exception f){

      }
      return true;
  }
  public static void main(String[] args){
    if(args.length == 0){
      welcome();
    }
    if(args.length == 1){
      if(args[0].equals("discord")){
        // check priv.
        System.out.println("discord setting up...");
      }
      else if(args[0].equals("messenger")){
        // check priv.
        System.out.println("messenger setting up...");
      }
      else if(args[0].equals("slack")){
        // check priv.
        System.out.println("slack setting up...");
      }
      String lol = "testing/phantomjs";//chromedriver"; // make this chromedriver.exe for Windows
      String discordChannel = "https://discordapp.com/channels/263162147792617482/263162147792617482"; // channel
      //System.setProperty("webdriver.chrome.driver", lol);
      System.setProperty("phantomjs.binary.path", lol);
      WebDriver driver = new PhantomJSDriver();// ChromeDriver();
      driver.manage().window().setSize(new Dimension(1124,850));
      try{
        driver.get(discordChannel);
        System.out.println("Scaffolding worked! "+ driver.getTitle());
        try{
          driver.findElement(By.id("register-email")).sendKeys("botto@haxsource.tech"); //email
          driver.findElement(By.id("register-password")).sendKeys("RlenzPS6"); // password
          driver.findElement(By.id("register-password")).submit();
        }
        catch(Exception l){

        }
        TimeUnit.SECONDS.sleep(5);
        if(driver.findElements(By.className("markdown-modal-close")).size() > 0){
          driver.findElement(By.className("markdown-modal-close")).click();
        }
        if(driver.findElements(By.xpath("//*[contains(text(), 'Skip')]")).size() > 0){
          driver.findElement(By.xpath("//*[contains(text(), 'Skip')]")).click();
        }
        driver.get(discordChannel);
        TimeUnit.SECONDS.sleep(2);
        //driver.get("https://discordapp.com//api/v6/channels/263472881525194752/messages?limit=50");
        System.out.println(getMessage(driver));
        WebElement account = driver.findElement(By.className("account"));
        String username = account.findElement(By.className("username")).getText();
        String profilePic = profilePicCheck(account,"small");
        String discriminator = account.findElement(By.className("discriminator")).getText();
        while(true){
          WebElement message = getMessageGroup(driver);
          String markup = getMarkup(message);
          if(!(getUsername(message).equals(username) && profilePicCheck(message).equals(profilePic))){
              if(markup.equals("hi")){
                send(driver,"hello " + getUsername(message));
                updateSleepCounter(driver,true);
              }
              else if(markup.equals("-time")){
                send(driver,getTimeStamp(message));
                updateSleepCounter(driver,true);
              }
              else if(markup.equals("say hi")){
                send(driver,"hi");
                updateSleepCounter(driver,true);
              }
              else if(markup.equals("-profilePicCheck")){
                send(driver,"I am");
                send(driver,username);
                send(driver,profilePic);
                send(driver,"message from");
                send(driver,profilePicCheck(message));
                updateSleepCounter(driver,true);
              }
              else if(markup.equals("-getDiscriminator")){
                send(driver,getDiscriminator(driver,message));
                updateSleepCounter(driver,true);
              }
              else if(markup.equals("break")){
                send(driver,"exiting loop");
                break;
              }
              else{
                updateSleepCounter(driver,false);
              }
          }
          else{
              updateSleepCounter(driver,false);
          }
          TimeUnit.SECONDS.sleep(sleepTime);
            //driver.navigate().refresh();
        }
        //System.out.println(driver.getPageSource());
        TimeUnit.SECONDS.sleep(1);
        driver.quit();
      }
      catch(Throwable e){
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        System.out.println(srcFile);
        try{
          FileUtils.copyFile(srcFile, new File("screen.png"));
        }
        catch(Exception f){

        }
        e.printStackTrace();
      }
    }
  }
}
