// THIS FILE HAS BEEN DEPRECATED AND IS FOR ARCHIVE USE ONLY
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;


<<<<<<< HEAD
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
  static ArrayList<String> command;
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
    System.out.println("\n                                Welcome to "+ ANSI_CYAN + "botto"+ANSI_RESET+"!\n\nbotto is an"+ANSI_PURPLE+" easy to set up framework"+ANSI_RESET+" that allows you to "+ANSI_YELLOW+"turn your device into an instant IoT device.\n\nbotto supports channels such as discord, fb messenger, and slack,"+ANSI_RESET+" to let "+ANSI_GREEN+"you make your own programmable recipes.\n\nProgram Usage:"+ANSI_PURPLE+"\njava Controller [option]\n\n"+ANSI_GREEN+"Options include:"+ANSI_PURPLE+"\ndiscord\nmessenger\nslack\n"+ANSI_RESET);
    Model.checkForSettings();
  }
    public static void getCommand(String markup){
    	command = new ArrayList<String>();
    	markup += " ";
    	int i = 0;
    	boolean backslash = false;
    	char x;
    	while(i < markup.length()){
    	    x = markup.charAt(i);
    	    if(x == '\\' && !backslash){
        		backslash = true;
        		markup = markup.substring(0,i) + markup.substring(i + 1, markup.length());
    	    }
    	    else if(x == ' ' && !backslash){
        		command.add(markup.substring(0,i));
        		markup = markup.substring(i + 1,markup.length());
        		i = -1;
    	    }
    	    else if(backslash){
        		backslash = false;
    	    }
    	      i++;
    	}
    }
    public static boolean commandCheck(String head,boolean unlimitedInputs, int minInputs,int maxInputs){
	     return command.get(0).equals(head) && command.size() - 1 >= minInputs && (unlimitedInputs || command.size() - 1 <= maxInputs);
=======
class Controller{
    private WebDriver driver;
    private String state;
    private String tag;
    public Controller(WebDriver driver, String tag){
	state = "on";
	this.driver = driver;
	this.tag = tag;
    }
    public String getTag(){
	return tag;
    }
    private void kill(){
	state = "dead";
	driver.quit();
    }
    public String getState(){
	return state;
    }
    int maxSleepTime = 5;
    int minSleepTime = 1;
    int sleepTime = 1;
    int pause = 0;
    int maxCounter = 300;
    int counter = 300;
    private void updateSleepCounter(WebDriver driver,boolean x){
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
    public void startup(){
	System.out.println("Controller class is meant to be extended");
    }
    public void tick(){
    }
    private void getCommand(String markuo){
    }
    private boolean commandCheck(String head,boolean unlimitedInputs,
				       int minInputs,int maxInputs){
    }
    private WebElement getMessageGroup(){
    }
    private static String profilePicCheck(WebElement message, String x){
    }
    private static List<WebElement> getMarkups(WebElement message){
    }
    private static String getUsername(WebElement messageGroup){
    }
    private static String getTimeStamp(WebElement messageGroup){
    }
    private static boolean send(WebDriver driver,String str){
    }

}

class Discord extends Controller{
    public Controller(WebDriver driver, String tag){
	super(driver,tag);
    }
    private static ArrayList<String> command;
    private void getCommand(String markup){
	command = new ArrayList<String>();
	markup += " ";
	int i = 0;
	boolean backslash = false;
	char x;
	while(i < markup.length()){
	    x = markup.charAt(i);
	    if(x == '\\' && !backslash){
		backslash = true;
		markup = markup.substring(0,i) + markup.substring(i + 1, markup.length());
	    }
	    else if(x == ' ' && !backslash){
		command.add(markup.substring(0,i));
		markup = markup.substring(i + 1,markup.length());
		i = -1;
	    }
	    else if(backslash){
		backslash = false;
	    }
	    i++;
	}
    }
    private boolean commandCheck(String head,boolean unlimitedInputs,
				       int minInputs,int maxInputs){
	return command.get(0).equals(head) && command.size() - 1 >= minInputs &&
	    (unlimitedInputs || command.size() - 1 <= maxInputs);
>>>>>>> 0d73d9a2bc6492f9836f4d1ced203557a793b339
    }
    private WebElement getMessageGroup(){
    List<WebElement> messages = driver.findElements(By.className("message-group"));
    int size = messages.size();
    return messages.get(size - 1);
  }
<<<<<<< HEAD
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
=======
    private static String profilePicCheck(WebElement message, String x){
     String url = message.findElement(By.className("avatar-" + x)).getAttribute("style");
     return url.substring(url.indexOf('"') + 1 ,url.lastIndexOf('"'));
    }
    private static String profilePicCheck(WebElement message){
	return profilePicCheck(message,"large");
    }
    private static List<WebElement> getMarkups(WebElement message){
        return message.findElements(By.className("markup"));
    }
    private static String getDiscriminator(WebDriver driver,WebElement message,String x){
	WebElement avatar = message.findElement(By.className("avatar-" + x));
	avatar.click();
	String discriminator = driver.findElement(By.className("user-popout"))
	    .findElement(By.className("discriminator"))
	    .getText();
	avatar.click();
	return discriminator;
    }
    private static String getDiscriminator(WebDriver driver,WebElement message){
	return getDiscriminator(driver,message,"large");
    }
    private static String getMarkup(WebElement message){
	int size = getMarkups(message).size();
	return getMarkups(message).get(size - 1).getText();
    }
    //getUsername is not a valid way of identification
    private static String getUsername(WebElement messageGroup){
	return messageGroup.findElement(By.className("user-name")).getText();
    }
    private static String getTimeStamp(WebElement messageGroup){
	return messageGroup.findElement(By.className("timestamp")).getText();
    }
    private static boolean send(WebDriver driver,String str){
>>>>>>> 0d73d9a2bc6492f9836f4d1ced203557a793b339
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
<<<<<<< HEAD
  }
  public static void main(String[] args){
    if(args.length == 0){
      welcome();
    }
    if(args.length == 1){
      String lol = "testing/phantomjs";//testing/chromedriver"; // make this chromedriver.exe for Windows
      //System.setProperty("webdriver.chrome.driver", lol);
      System.setProperty("phantomjs.binary.path", lol);
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:16.0) Gecko/20121026 Firefox/16.0");
      WebDriver driver = new PhantomJSDriver(capabilities);// ChromeDriver();
      driver.manage().window().setSize(new Dimension(1124,850));
      if(args[0].equals("discord")){
        // check priv.
        System.out.println("discord setting up...");
        String discordChannel = "https://discordapp.com/channels/263162147792617482/263162147792617482"; // channel
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
          String oldMessage = getMessageGroup(driver).getText();
          String newMessage;
          WebElement message;
          String markup;
          while(true){
            message = getMessageGroup(driver);
        	  markup = getMarkup(message);
        	  newMessage = message.getText();
        	  if(!(oldMessage.equals(newMessage))){
        	      if(!(getUsername(message).equals(username) && profilePicCheck(message).equals(profilePic))){
            		  getCommand(markup);
            		  if(command.size() == 0){
            		      command.add("null");
            		  }
            		  System.out.println(command);
            		  if(markup.charAt(0) == '-'){
          		      if(commandCheck("-time",false,0,0)){
              			  send(driver,getTimeStamp(message));
          		      }
        		      else if(commandCheck("-profilePicCheck",false,0,0)){
            			  send(driver,"I am");
            			  send(driver,username);
            			  send(driver,profilePic);
            			  send(driver,"message from");
            			  send(driver,profilePicCheck(message));
        		      }
        		      else if(commandCheck("-getDiscriminator",false,0,0)){
            			  send(driver,getDiscriminator(driver,message));
        		      }
        		      else if(commandCheck("-break",false,0,0)){
            			  send(driver,"exiting loop");
            			  break;
        		      }
        		      updateSleepCounter(driver,true);
        		  }
        		  else{
        		      if(commandCheck("hi",false,0,1)){
            			  if(command.size() == 2){
          			      if (command.get(1).equals(username)){
              				  send(driver,"hello, that's me");
        			        }
        			      }
        			      else{
        			        send(driver,"hello " + getUsername(message));
        			      }
        		      }
        		      else if(commandCheck("say",false,1,1)){
        			         send(driver,command.get(1));
        		      }
        		      else if(commandCheck("break",false,0,0)){
        			         send(driver,"the break command has been changed to -break");
        		      }
        		      updateSleepCounter(driver,true);
        		  }
        		  oldMessage = newMessage;
        	  }
        	  }
        	  updateSleepCounter(driver,false);
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
      else if(args[0].equals("messenger")){
        // check priv.
        try{
          System.out.println("messenger setting up...");
          String messengerChannel = "https://mbasic.facebook.com";
          driver.get(messengerChannel);
          driver.findElement(By.className("_5ruq")).sendKeys("jonathan@haxsource.com");
          driver.findElement(By.className("_27z3")).sendKeys("bottodemo1234");
          driver.findElement(By.className("_27z3")).submit();
          driver.get("https://mbasic.facebook.com/messages/read/?fbid=1856862454602853&_rdr");
          try{
            TimeUnit.SECONDS.sleep(2);
          }
          catch(Exception e){

          }
          //driver.findElement(By.className("_5f0v")).click();

          //WebElement kmc = driver.findElement(By.className("_5rpu"));
          //kmc.findElement(By.xpath("div/div")).sendKeys("testing... (Y)");
          driver.findElement(By.tagName("textarea")).sendKeys("after several days... it works... 👊");
          driver.findElement(By.tagName("textarea")).submit();
          //WebElement sender = driver.findElement(By.xpath("//*[contains(text(), 'Send')]"));
          //WebElement sender = driver.findElements(By.className("_30yy")).get(1);
          //System.out.println(sender.getCssValue("display"));
          //((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility='visible';", sender);
          //sender.click();
          //System.out.println(driver.getTitle());
          File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
          System.out.println(srcFile);
          String text = "return document.body.innerHTML;";
          String html = ((JavascriptExecutor) driver).executeScript(text).toString();
          try{
            FileUtils.copyFile(srcFile, new File("screen.png"));
            FileUtils.writeStringToFile(new File("err.html"), html, "UTF-8");
          }
          catch(Exception f){

          }
          driver.quit();
        }
        catch(Exception e){
          driver.quit();
          try{
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File("screen.png"));
          }
          catch(Exception f){

          }
          e.printStackTrace();
        }
      }
      else if(args[0].equals("slack")){
        // check priv.
        System.out.println("slack setting up...");
      }
=======
    }
    WebElement account;
    String username;
    String profilePic;
    String discriminator;
    String oldMessage;
    String newMessage;
    WebElement message;
    String markup;
    public void startup(){
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


	    account = driver.findElement(By.className("account"));
	    username = account.findElement(By.className("username")).getText();
	    profilePic = profilePicCheck(account,"small");
	    discriminator = account.findElement(By.className("discriminator")).getText();
	    oldMessage = getMessageGroup(driver).getText();
	    newMessage;
	    message;
	    markup;
	    catch(Throwable e){
		e.printStackTrace();
	    }
	}
    }
    public void tick(){
	if(pause <= 0 and state.equals("on")){
	    pause = sleepTime;
	try{
	    message = getMessageGroup();
	    markup = getMarkup(message);
	    newMessage = message.getText();
	    if(!(oldMessage.equals(newMessage))){
		if(!(getUsername(message).equals(username) &&
		     profilePicCheck(message).equals(profilePic))){
		    getCommand(markup);
		    if(command.size() == 0){
			command.add("null");
		    }
		    //System.out.println(command);
		    if(markup.charAt(0) == '-'){
			if(commandCheck("-time",false,0,0)){
			    send(driver,getTimeStamp(message));
			}
			else if(commandCheck("-profilePicCheck",false,0,0)){
			    send(driver,"I am");
			    send(driver,username);
			    send(driver,profilePic);
			    send(driver,"message from");
			    send(driver,profilePicCheck(message));
			}
			else if(commandCheck("-getDiscriminator",false,0,0)){
			    send(driver,getDiscriminator(driver,message));
			}
			else if(commandCheck("-break",false,0,0)){
			    send(driver,"exiting loop");
			    kill();
			}
			updateSleepCounter(driver,true);
		    }
		    else{
			if(commandCheck("hi",false,0,1)){
			    if(command.size() == 2){
				if (command.get(1).equals(username)){
				    send(driver,"hello, that's me");
				}
			    }
			    else{
				send(driver,"hello " + getUsername(message));
			    }
			}
			else if(commandCheck("say",false,1,1)){
			    send(driver,command.get(1));
			}
			else if(commandCheck("break",false,0,0)){
			    send(driver,"the break command has been changed to -break");
			}
			updateSleepCounter(driver,true);
		    }
		    oldMessage = newMessage;
		}
	    }
	    updateSleepCounter(driver,false);
	}
	//System.out.println(driver.getPageSource());
	catch(Throwable e){
	    e.printStackTrace();
	}
	}
	else{
	    pause--;
	}
>>>>>>> 0d73d9a2bc6492f9836f4d1ced203557a793b339
    }
}
