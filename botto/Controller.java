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

class Discord{
    private int maxSleepTime = 5;
    private int minSleepTime = 1;
    private int sleepTime = 1;
    private int maxCounter = 300;
    private int counter = 300;
    private WebDriver driver;
    public Controller(String lol){
	System.setProperty("webdriver.chrome.driver", lol);
	driver = new ChromeDriver();
    }
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
    static ArrayList<String> command;
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
    }
  private WebElement getMessageGroup(){
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
    public void begin();
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
	String oldMessage = getMessageGroup(driver).getText();
	String newMessage;
	WebElement message;
	String markup;
	while(true){
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
	}
	//System.out.println(driver.getPageSource());
	TimeUnit.SECONDS.sleep(1);
	driver.quit();
    }
    catch(Throwable e){
	e.printStackTrace();
    }
}
