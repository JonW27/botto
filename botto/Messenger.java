
import java.util.*;
import java.io.*;
//import javax.mail.*;
import java.util.concurrent.TimeUnit;
import java.net.InetAddress;
//import com.sun.mail.smtp.*;
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
import org.apache.commons.io.FileUtils;

class Messenger extends Controller{
    //private String page = ""; // FB page for communication
    Messenger(int index,WebDriver driver){
    	super(index,"messenger",driver);
    }
    void kill(){
    	setState("dead");
    	driver.quit();
    }
    private ArrayList<String> command;
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
    List<WebElement> messages = driver.findElements(By.xpath("//div[contains(@class, 'msg')]/div/span"));
    int size = messages.size();
    return messages.get(size - 1);
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
    //getUsername is not a valid way of identification
    String getUsername(WebElement messageGroup){
	return messageGroup.findElement(By.className("user-name")).getText();
    }
    String getTimeStamp(WebElement messageGroup){
      Calendar calendar = Calendar.getInstance();
      SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	    return format.format(calendar.getTime());
    }
    void send(String str){
      driver.findElement(By.tagName("textarea")).sendKeys(str);
      driver.findElement(By.tagName("textarea")).submit();
    }
    //WebElement account;
    //String username;
    //String profilePic;
    //String discriminator;
    String oldMessage;
    String newMessage;
    Model attempt = new Model("messenger");
    String page = attempt.getStream();
    WebElement message;
    //String markup;
    public boolean startup(){
      if(!attempt.getConfig()){
        System.out.println("You don't have an mnf.botto file. Please run java Botto to begin setup.");
        kill();
        return false;
      }
      if(!attempt.channelExists()){
        System.out.println("You did not set-up credentials for messenger in your mnf.botto file.");
        kill();
        return false;
      }
	try{
      String messengerChannel = "https://mbasic.facebook.com";
      driver.get(messengerChannel);
      driver.findElement(By.className("_5ruq")).sendKeys(attempt.getUsername());
      driver.findElement(By.className("_27z3")).sendKeys(attempt.getPassword());
      driver.findElement(By.className("_27z3")).submit();
	    System.out.println("Scaffolding worked! "+ driver.getTitle());
      driver.get("https://mbasic.facebook.com/messages/read/?fbid="+page+"&_rdr");
      // got to fix below
	    //System.out.println(getMessageGroup(driver));


	    //account = driver.findElement(By.className("account"));
	    //username = account.findElement(By.className("username")).getText();
	    //profilePic = profilePicCheck(account,"small");
	    //discriminator = account.findElement(By.className("discriminator")).getText();
	    oldMessage = getMessageGroup().getText();
	}
	catch(Throwable e){
    File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    System.out.println(srcFile);
    try{
      FileUtils.copyFile(srcFile, new File("screen.png"));
    }
    catch(Exception f){

    }
      driver.quit();
	    e.printStackTrace();
	}
	return true;
    }
    boolean tick(){
	if(pause <= 0 && getState().equals("on")){
	    pause = sleepTime;
      driver.navigate().refresh();
	    try{
		message = getMessageGroup();
		//markup = getMarkup(message);
		newMessage = message.getText();
		if(!(oldMessage.equals(newMessage))){
      getCommand(message.getText());
			if(command.size() == 0){
			    command.add("null");
			}
			//System.out.println(command);
			if(message.getText().charAt(0) == '-'){
			    if(commandCheck("-time",false,0,0)){
            System.out.println("checked msg");
				send((getTimeStamp(message)));
			    }
          else if(commandCheck("-term",false,1,20)){
            System.out.println("terminal command launched");
            String output = "";
            try{
          	    String command = newMessage.substring(5);
                String[] commands = command.split("\\|", -1);
                /*if(Arrays.asList(commands).contains("cd")){

                }
                else{*/
                  Process proc = Runtime.getRuntime().exec(command);

            	    // Read the output

            	    BufferedReader reader =
            		new BufferedReader(new InputStreamReader(proc.getInputStream()));
            	    String line = "";
            	    while((line = reader.readLine()) != null) {
            		output = output + line + "\n";
            	    //}
                }
          	    proc.waitFor();
          	}
          	catch(Throwable e){
          	    e.printStackTrace();
          	}
            if(!output.equals("")){
              send(output);
            }
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
				    send("hello ");// + getUsername(message)); doesn't really matter since it's just forwarding through a page
			    }
			    else if(commandCheck("say",false,1,1)){
				send(command.get(1));
			    }
			    else if(commandCheck("break",false,0,0)){
				send("the break command has been changed to -break");
			    }
          else if(newMessage.equals("quote mr k")){
            String mrkquotes[] = {"I can say that during a plague, I would be more proactive in ensuring that sick people are burned.",
"Isn't it okay to hang one student per year so that the rest of them are happy?", "If I hated you, I can guarantee that you would not know.", "I'm not racist, I just go around quoting Rza lyrics.", "If the only thing I teach you is when not to speak, then I will have done the world a great service.", "The next time you do that, I will fix you",
"Best Friends Forever are really just Best Friends For Now, unless one of you is dead already.", "If I were let loose in a room full of anti-vaxxers...I would only want that if I had a vial of measles.",
"You are the Stable Boy Scrubber!", "When I said I would fix him, I didn't know what I was agreeing to. It's like when you agree to shovel someone's driveway and they live 300ft. into their property.", "Some pokemon look tastier than others.", "On the bright side, they won't live long enough to retaliate", "Students are killable", "I can hit students as long as they don't survive to complain about it"};
            Random picker = new Random();
            send(mrkquotes[picker.nextInt(mrkquotes.length)]);
          }
          else if(newMessage.equals("quote mr brown")){
            String mrbrownquotes[] = {"Binary Feedback!","Thumbs!", "Damn right you are!", "Yip Yip Yip, TRIO, TRIO!!!", "Procure a KtS", "Volunteers?", "Do not go onto the Interwebz.", "Daz a nice!", "I can’t keep calling on PChan", "PChan has reached his quota for the period", "Don’t turn on your machines just yet", "Always look at the QAF!!", "Easy tigers!", "You are the next hope of this country.", "JA", "My other period of thinkers....", "Thinkers!", "‘Twas an epic battle, to be sure… You cut ye old monster down, but with its dying breath ye old monster laid a fatal blow upon thy skull.", "Ye olde self hath expired. You got dead.", "Damn right, son!", "Mr. Me", "Get your collectibles", "I smell something in the oven!", "A day of relaxation", "Breathe….", "Song of the week"};
            Random picker = new Random();
            send(mrbrownquotes[picker.nextInt(mrbrownquotes.length)]);

          }
			    updateSleepCounter(true);
			}
			oldMessage = newMessage;
		}
		updateSleepCounter(false);
	    }
	    //System.out.println(driver.getPageSource());
	    catch(StringIndexOutOfBoundsException e){
		send("nice picture");
	    }
	    catch(Throwable e){
		      e.printStackTrace();
          driver.get("https://mbasic.facebook.com/messages/read/?fbid="+page+"&_rdr");
          send("That's an error mate.");
          try{
            TimeUnit.SECONDS.sleep(1);
          }
          catch(Exception q){

          }
	    }
	}
	else{
	    pause--;
	}
	return true;
    }
}
