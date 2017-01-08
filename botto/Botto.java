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
    public static String chromePath = "testing/chromedriver.exe";
}
public class Botto{
    private static ArrayList<Controller> Controllers = new ArrayList<Controller>();
    private static Controller getController(int index){
	return Controllers.get(index);
    }
    private static Controller makeController(String tag){
	WebDriver driver = new ChromeDriver();
	Controller discord = new Discord(driver,"discord");
	Controllers.add(discord);
	return discord;
    }
    private static int tickLength = 1;
    private static int getTickLength(){
	return tickLength;
    }
    private static int getControllerIndex(String tag){
    	for(int i = 0;i < Controllers.size();i++){
    	    if(getController(i).getTag().equals(tag)){
    		return i;
    	    }
   	}
	return -1;
    }
    private static void removeController(int index){
	Controllers.remove(index);
    }
    public static void main(String[] args){
	System.setProperty("webdriver.chrome.driver", info.chromePath);
	makeController("discord").startup();
	System.out.println("started up discord");
	try{
	    while(Controllers.size() > 0){
		for(int i = 0;i < Controllers.size();i++){
		    if(getController(i).getState().equals("dead")){
			removeController(i);
			i--;
		    }
		    else{
			getController(i).tick();
		    }
		}
		TimeUnit.SECONDS.sleep(tickLength);
	    }
	}
	catch(Throwable e){
	    e.printStackTrace();
	}
    }
}
class Controller{
    private String state;
    private String tag;
    Controller(String tag){
	state = "on";
	this.tag = tag;
    }
    public String getTag(){
	return tag;
    }
    private void kill(){
	state = "dead";
    }
    public String getState(){
	return state;
    }
    void setState(String x){
	state = x;
    }
    int maxSleepTime = 5;
    int minSleepTime = 1;
    int sleepTime = 1;
    int pause = 0;
    int maxCounter = 300;
    int counter = 300;
    void updateSleepCounter(boolean x){
	if(x){
	    counter = maxCounter;
	}
	else{  
	    if(counter == maxCounter){
		
		sleepTime = minSleepTime;
	    }
	    if(counter > 0){
		counter -= 1;
	    }
	    else if(counter == 0){
		
		sleepTime = maxSleepTime;
		counter -= 1;
	    }
	}
    }
    public boolean startup(){
	System.out.println("Controller class is meant to be extended");
	return false;
    }
    public boolean tick(){
	return false;
    }
}

class Discord extends Controller{
    private WebDriver driver;
    public Discord(WebDriver driver, String tag){
	super(tag);
	this.driver = driver;
    }
    private void kill(){
	setState("dead");
	driver.quit();
    }
    private ArrayList<String> command;
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
    private String profilePicCheck(WebElement message, String x){
     String url = message.findElement(By.className("avatar-" + x)).getAttribute("style");
     return url.substring(url.indexOf('"') + 1 ,url.lastIndexOf('"'));
    }
    private String profilePicCheck(WebElement message){
	return profilePicCheck(message,"large");
    }
    private List<WebElement> getMarkups(WebElement message){
        return message.findElements(By.className("markup"));
    }
    private String getDiscriminator(WebDriver driver,WebElement message,String x){
	WebElement avatar = message.findElement(By.className("avatar-" + x));
	avatar.click();
	String discriminator = driver.findElement(By.className("user-popout"))
	    .findElement(By.className("discriminator"))
	    .getText();
	avatar.click();
	return discriminator;
    }
    private String getDiscriminator(WebDriver driver,WebElement message){
	return getDiscriminator(driver,message,"large");
    }
    private String getMarkup(WebElement message){
	int size = getMarkups(message).size();
	return getMarkups(message).get(size - 1).getText();
    }
    //getUsername is not a valid way of identification
    private String getUsername(WebElement messageGroup){
	return messageGroup.findElement(By.className("user-name")).getText();
    }
    private String getTimeStamp(WebElement messageGroup){
	return messageGroup.findElement(By.className("timestamp")).getText();
    }
    private void send(String str){
      driver.findElement(By.tagName("textarea")).sendKeys(str);
      driver.findElement(By.tagName("textarea")).sendKeys(Keys.RETURN);
    }
    WebElement account;
    String username;
    String profilePic;
    String discriminator;
    String oldMessage;
    String newMessage;
    WebElement message;
    String markup;
    public boolean startup(){
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
	    oldMessage = getMessageGroup().getText();
	}
	catch(Throwable e){
	    e.printStackTrace();
	}
	return true;
    }
    public boolean tick(){
	if(pause <= 0 && getState().equals("on")){
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
				send(getTimeStamp(message));
			    }
			    else if(commandCheck("-profilePicCheck",false,0,0)){
				send("I am");
				send(username);
				send(profilePic);
				send("message from");
				send(profilePicCheck(message));
			    }
			    else if(commandCheck("-getDiscriminator",false,0,0)){
				send(getDiscriminator(driver,message));
			    }
			    else if(commandCheck("-break",false,0,0)){
			        send("exiting loop");
				TimeUnit.SECONDS.sleep(1);
				kill();
				
			    }
			    updateSleepCounter(true);
			}
			else{
			    if(commandCheck("hi",false,0,1)){
				if(command.size() == 2){
				    if (command.get(1).equals(username)){
					send("hello, that's me");
				    }
				}
				else{
				    send("hello " + getUsername(message));
				}
			    }
			    else if(commandCheck("say",false,1,1)){
				send(command.get(1));
			    }
			    else if(commandCheck("break",false,0,0)){
				send("the break command has been changed to -break");
			    }
			    updateSleepCounter(true);
			}
			oldMessage = newMessage;
		    }
		}
		updateSleepCounter(false);
	    }
	    //System.out.println(driver.getPageSource());
	    catch(StringIndexOutOfBoundsException e){
		send("nice picture");
	    }
	    catch(Throwable e){
		e.printStackTrace();
	    }
	}
	else{
	    pause--;
	}
	return true;
    }
}
