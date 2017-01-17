
import java.util.*;
import java.io.*;
//import javax.mail.*;
import java.util.concurrent.TimeUnit;
import java.net.InetAddress;
//import com.sun.mail.smtp.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import java.text.SimpleDateFormat;

class info{
    static String os = System.getProperty("os.name");
    static String chromePath = "testing/chromedriver.exe";
    static String phantomPath = "testing/phantomjs.exe";
    static int determine;
    static boolean headless = Model.yesNoPrompt("Use phantomjs (headless) to reduce overhead, instead of chrome browser?");
    static void info(){
      if(os.equals("Windows")){
        chromePath = "testing/chromedriver.exe";
        phantomPath = "testing/phantomjs.exe";
      }
      else if(os.equals("Mac OS X")){
        chromePath = "testing/chromedriver";
        phantomPath = "testing/phantomjs";
      }
    System.out.println("Using "+chromePath+" or "+phantomPath);
    }
}
public class Botto{
    // ANSI COLORS FOR WELCOME
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";
    //
    private static ArrayList<Controller> Controllers = new ArrayList<Controller>();
    private static ArrayList<WebDriver> Drivers = new ArrayList<WebDriver>();
    private static void reset(){
	String[] command = {"cp","PluginState0/Plugin0.java", "Plugin0.java"};
	String[] command2 = {"cp","PluginState0/Plugin1.java","Plugin1.java"};
	String[] command3 = {"cp","PluginState0/PluginTemplate.java","PluginTemplate.java"};
	try{
	    terminalCommand.go(command);
	    terminalCommand.go(command2);
	    terminalCommand.go(command3);
	}
	catch(Throwable e){
	    e.printStackTrace();
	}
    }
    static Controller getController(int index){
	return Controllers.get(index);
    }
    static WebDriver getDriver(int index){
	return Drivers.get(index);
    }
    static Controller addController(Controller x){
	Controllers.add(x);
	Drivers.add(x.driver);
	return x;
    }
    private static Controller makeController(String tag, int determine){
      WebDriver driver;
      if(info.headless){
        driver = new PhantomJSDriver();
      }
      else{
        driver = new ChromeDriver();
      }
      driver.manage().window().setSize(new Dimension(1124,850));
      /* working on it
      if(determine == 0){
	  Controller p = lastPlugin.nextPlugin(i,driver,"plugin");
	  Controllers.add(p);
	  pluginNum++;
	  return p;
      }
      */
      if(determine == 1){
	  Controller discord = new Discord(getControllersSize(),driver);
	  Controllers.add(discord);
	  Drivers.add(driver);
	  return discord;
      }
      else if(determine == 2){
	  Controller messenger = new Messenger(getControllersSize(),driver, "1856862454602853"); // the last arg is the fbid, which can be found from the url of messenger
	  Controllers.add(messenger);
	  Drivers.add(driver);
	  return messenger;
      }
      else{
	  Controller x = new Controller("default",false);
	  System.out.println("defaulting to Controller, did you spell something wrong?");
	  return x;
      }
    }
    private static int tickLength = 1;
    static int getTickLength(){
	return tickLength;
    }
    static int getControllersSize(){
	return Controllers.size();
    }
    static int getControllerIndex(String tag){
    	for(int i = 0;i < Controllers.size();i++){
    	    if(getController(i).getTag().equals(tag)){
    		return i;
    	    }
   	}
	return -1;
    }
    static void removeController(){
	Controllers.remove(i);
	Drivers.remove(i);
	ii -= 1;
	i--;
    }
    static void welcome(){
      System.out.println("\n                                Welcome to "+ ANSI_CYAN + "botto"+ANSI_RESET+"!\n\nbotto is an"+ANSI_PURPLE+" easy to set up framework"+ANSI_RESET+" that allows you to "+ANSI_YELLOW+"turn your device into an instant IoT device.\n\nbotto supports channels such as discord, fb messenger, and slack,"+ANSI_RESET+" to let "+ANSI_GREEN+"you make your own programmable recipes.\n\nProgram Usage:"+ANSI_PURPLE+"\njava Controller [option]\n\n"+ANSI_GREEN+"Options include:"+ANSI_PURPLE+"\ndiscord\nmessenger\nslack\n"+ANSI_RESET);
      Model.checkForSettings();
    }
    private static void setValues(){
	System.setProperty("webdriver.chrome.driver", info.chromePath);
	System.setProperty("phantomjs.binary.path", info.phantomPath);
    }
    private static int i = 0;
    private static int ii = 0;
    static int getI(){
	return i;
    }
    static int getIi(){
	return ii;
    }
    public static void main(String[] args){
	if(args.length == 0){
    welcome();
    Model.checkForSettings();
	}
	else if(args.length == 1){
	    info.info();
	    setValues();
	    if(args[0].equals("discord")){
	info.determine = 1;
	    }
	    else if(args[0].equals("messenger")){
		info.determine = 2;
	    }
	    else if(args[0].equals("reset")){
		reset();
		info.determine = 3;
	    }
	}
	else if(args.length == 2){
	    info.info();
	    setValues();
	    if(args[0].equals("plugin")){
		info.determine = 0;
	    }
	}
	if(args.length > 0){
	    if(info.determine != 3){
		reset();
		makeController(args[0], info.determine).startup();
		System.out.println("Started up " + args[0]);
	    }
	    while(Controllers.size() > 0){
		ii = 0;
		for(i = 0;i < Controllers.size();i++){
		    try{
			if(getController(i).getState().equals("dead")){
			    removeController();
			}
			else{
			    if(!(getController(i).getState().equals("off"))){
				getController(i).correctIndex();
				getController(i).tick();
			    }
			}
		    }
		    catch(Throwable e){
			try{
			    int backup = getController(i).getParentIndex();
			    if(backup != -1){
				getController(backup).setState("on");
			    }
			}
			catch(Throwable f){
			    System.out.println("STAP OVERWRITING MY METHODS!!!");
			}
			removeController();
			e.printStackTrace();
		    }
		    try{
			TimeUnit.SECONDS.sleep(tickLength);
		    }
		    catch(Throwable e){
			e.printStackTrace();
		    }
		}
	    }   
	}
    }
}
class Controller{
    //don't use this class, its only meant to be extended
    private String state;
    private String tag;
    private int index_;
    private int parentIndex_ = -1;
    WebDriver driver;
    int getIndex(){
	return index_;
    }
    int getParentIndex(){
	return parentIndex_;
    }
    void correctIndex(){
	index_ += Botto.getIi();
    }
    void send(String str){
	if(str.length() > 2000){
	    driver.findElement(By.tagName("textarea")).sendKeys("over 2000 chars in length");
	    driver.findElement(By.tagName("textarea")).sendKeys(Keys.RETURN);
	}
	else{
	    driver.findElement(By.tagName("textarea")).sendKeys(str);
	    driver.findElement(By.tagName("textarea")).sendKeys(Keys.RETURN);
	}
    }
    Controller(){
	state = "off";
	tag = "firstPlugin";
    }
    Controller(int index,int parentIndex,String tag,WebDriver driver){
	index_ = index;
	parentIndex_ = parentIndex;
	this.driver = driver;
	state = "on";
	this.tag = tag;
	
    }
    Controller(int index,String tag,WebDriver driver){
	state = "on";
	this.tag = tag;
	index_ = index;
	this.driver = driver;
    }
    Controller(String tag,Boolean death){
	if(death == false){
	    state = "dead";
	    this.tag = tag;
	}
    }
    String getTag(){
	return tag;
    }
    void kill(){
	state = "dead";
    }
    String getState(){
	return state;
    }
    void setState(String x){
	state = x;
    }
    int sleepTime = 1;
    int maxSleepTime = 5;
    int minSleepTime = 1;
    int pause = 0;
    int maxCounter = 300;
    int counter = 300;
    void makeErrorReport(Exception e){
	StringWriter errors = new StringWriter();
	e.printStackTrace(new PrintWriter(errors));
	try{
	    send("plugin crashed");
	    send(errors.toString());
	}
	catch(Exception f){
	    System.out.println("plugin crashed");
	    f.printStackTrace();
	}
	try{
	    FileWriter a = new FileWriter("crash-report.log",true);
	    BufferedWriter b = new BufferedWriter(a);
	    PrintWriter writer = new PrintWriter(b);
	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	    writer.println(format.format(calendar.getTime()) + "\n");
	    writer.println(errors.toString() + "\n");
	    writer.println("-------------------\n");
	}
	catch (IOException g){
	    System.out.println("all hope is lost, all try blocks have failed");
	    g.printStackTrace();
	}
    }
    void runPluginDash(){
	try{
	    //just a format
	}
	catch(Exception e){
	    makeErrorReport(e);
	}
    }
    void runPlugin(){
	try{
	    //just a format
	}
	catch(Exception e){
	    makeErrorReport(e);
	}
    }
    boolean startup(){
	System.out.println("this class is not meant to be started up");
	return false;
    }
    boolean tick(){
	return false;
    }
    Controller nextPlugin(int index,int parentIndex,WebDriver driver){
	Controller plugin = new Plugin0(index,parentIndex,driver);
	return plugin;
    }
}

class Discord extends Controller{
    Discord(int index, WebDriver driver){
	super(index,"discord",driver);
  driver.manage().window().setSize(new Dimension(1124,850));
    }
    Discord(int index,String tag,WebDriver driver){
	super(index,tag,driver);
	driver.manage().window().setSize(new Dimension(1124,850));
    }
    Discord(int index,int parentIndex,String tag,WebDriver driver){
	super(index,parentIndex,tag,driver);
	driver.manage().window().setSize(new Dimension(1124,850));
    }
    void kill(){
	setState("dead");
	driver.quit();
    }
    ArrayList<String> command;
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
    void getCommand(String markup){
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
    boolean commandCheck(String head,boolean unlimitedInputs,
				       int minInputs,int maxInputs){
	return command.get(0).equals(head) && command.size() - 1 >= minInputs &&
	    (unlimitedInputs || command.size() - 1 <= maxInputs);
    }
    WebElement getMessageGroup(){
    List<WebElement> messages = driver.findElements(By.className("message-group"));
    int size = messages.size();
    return messages.get(size - 1);
  }
    String profilePicCheck(WebElement message, String x){
     String url = message.findElement(By.className("avatar-" + x)).getAttribute("style");
     return url.substring(url.indexOf('"') + 1 ,url.lastIndexOf('"'));
    }
    String profilePicCheck(WebElement message){
	return profilePicCheck(message,"large");
    }
    List<WebElement> getMarkups(WebElement message){
        return message.findElements(By.className("markup"));
    }
    String getDiscriminator(WebDriver driver,WebElement message,String x){
	WebElement avatar = message.findElement(By.className("avatar-" + x));
	avatar.click();
	String discriminator = driver.findElement(By.className("user-popout"))
	    .findElement(By.className("discriminator"))
	    .getText();
	avatar.click();
	return discriminator;
    }
    String getDiscriminator(WebDriver driver,WebElement message){
	return getDiscriminator(driver,message,"large");
    }
    String getMarkup(WebElement message){
	int size = getMarkups(message).size();
	return getMarkups(message).get(size - 1).getText();
    }
    //getUsername is not a valid way of identification
    String getUsername(WebElement messageGroup){
	return messageGroup.findElement(By.className("user-name")).getText();
    }
    String getTimeStamp(WebElement messageGroup){
	return messageGroup.findElement(By.className("timestamp")).getText();
    }
    void send(String str){
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
    String oldUsername;
    String newUsername;
    boolean startup(){
	try{
	    driver.get("https://discordapp.com/channels/263162147792617482/263162147792617482");
      TimeUnit.SECONDS.sleep(2);
	    System.out.println("Scaffolding worked! "+ driver.getTitle());
      File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(srcFile, new File("screen.png"));
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
		TimeUnit.SECONDS.sleep(1);
		driver.findElement(By.xpath("//*[contains(text(), 'Skip')]")).click();
	    }
	    driver.get("https://discordapp.com/channels/263162147792617482/263162147792617482");
      File srcFil = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(srcFil, new File("screen.png"));
	    //System.out.println(getMessageGroup(driver));


	    account = driver.findElement(By.className("account"));
	    username = account.findElement(By.className("username")).getText();
	    profilePic = profilePicCheck(account,"small");
	    discriminator = account.findElement(By.className("discriminator")).getText();
	    WebElement x = getMessageGroup();
	    oldMessage = x.getText();
	    oldUsername = getUsername(x);
	}
	catch(Throwable e){
      File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      try{
        FileUtils.copyFile(srcFile, new File("screen.png"));
      }
      catch(Exception f){

      }
	    e.printStackTrace();
	}
	return true;
    }
    boolean tick(){
	if(!(getState().equals("off"))){
	    if(pause <= 0){
		pause = sleepTime;
		try{
		    message = getMessageGroup();
		    markup = getMarkup(message);
		    newMessage = message.getText();
		    newUsername = getUsername(message);
		    if(!(oldMessage.equals(newMessage) && oldUsername.equals(newUsername))){
			if(!(getUsername(message).equals(username) &&
			     profilePicCheck(message).equals(profilePic))){
			    if(getState().equals("on")){
				    getCommand(markup);
				    if(command.size() == 0){
					command.add("null");
				    }
				    //System.out.println(command)
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
					else if(commandCheck("-term",false,1,20)){
					    String[] words = new String[command.size()];
					    for(int i = 0; i < command.size(); i++){
						words[i] = command.get(i);
					    }
					    send("terminal command launched");
					    send(terminalCommand.go(words));
					}
					else if(commandCheck("-break",false,0,0)){
					    send("exiting loop");
					    TimeUnit.SECONDS.sleep(1);
					    kill();
					}
					else if(commandCheck("-extensionPlugin",false,0,0)){
					    send("setting state to extensionPlugin");
					    send("make sure to name your class PluginTemplate and that the method nextPlugin() returns a controller named PluginSomeNumber");
					    send("don't edit the toBeReplaced comment and add a _ in the beginning of your names to prevent accidentally overriding stuff");
					    setState("AcceptExtensionPlugin");
					}
					else{
					    runPluginDash();
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
					else{
					    runPlugin();
					}
				    }
			    }
				else if(getState().equals("AcceptExtensionPlugin")){
				    setState("on");
				    String PluginName = "Plugin" + PluginManager.getPluginNum();
				    String PluginNameJava = PluginName + ".java";
				    String replacedMarkup = markup.replace("PluginTemplate",PluginName).replace("PluginSomeNumber","Plugin" + (PluginManager.getPluginNum() + 1))
					.replace("/*toBeReplaced","//replaced").replace("toBeReplaced*/","//replaced");
				    //backup
				    String cp[] = {"cp",PluginNameJava,"pluginBackup.java"};
				    boolean backupFailed = false;
				    try{
					terminalCommand.go(cp);
				    }
				    catch(Throwable e){
					e.printStackTrace();
					backupFailed = true;
					send("backup Failed");
				    }
				    Model.writeToFile(replacedMarkup,PluginNameJava);
				    if(PluginManager.extensionTypePlugin(Botto.getControllersSize(),this,PluginNameJava)){
					send("plugin installed, use at your own risk");
				    }
				    else{
					send("plugin installation failed, go retake comp sci");
					send(PluginManager.errorMessage);
					if(!backupFailed){
					    cp[1] = "pluginBackup.java";
					    cp[2] = PluginNameJava;
					    String cp2[] = {"cp",PluginNameJava,"failedPlugin.java"};
					    try{
						terminalCommand.go(cp2);
						terminalCommand.go(cp);
						send("but I backed up your files so everything's ok");
					    }
					    catch(Throwable f){
						f.printStackTrace();
						send("backup failed gg");
					    }
					}
					else{
					    send("no backup gg");
					}
				    }
				}
				updateSleepCounter(true);
				}
			    oldMessage = newMessage;
			    oldUsername = newUsername;
			}
			updateSleepCounter(false);
		    } //System.out.println(driver.getPageSource());
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
	}
	return true;
    }
}
