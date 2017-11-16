import java.text.DateFormat;
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
    Mode mode;
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
	List<WebElement> messages = driver.findElements(By.className("e"));
    int size = messages.size();
    return messages.get(size - 2);
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
    String getLastMessage(String str){
	int mark = str.length() - 1;
	while(mark > 0){
	    if(str.charAt(mark) == '\n'){
		mark++;
		break;
	    }
	    mark--;
	}
	return str.substring(mark,str.length());
    }
    String removeTimeStamp(String str){
	for(int i = str.length() - 1;i > 0;i--){
	    if(str.charAt(i) == '\n'){
		return str.substring(0,i);
	    }

	}
	return "";
    }
    String getUserName2(String str){
	for(int i = 0;i < str.length();i++){
	    if(str.charAt(i) == '\n'){
		return str.substring(0,i);
	    }

	}
	return "";

    }

    //WebElement account;
    //String username;
    //String profilePic;
    //String discriminator;
    String oldMessage;
    String newMessage;
    //Model attempt = new Model("messenger");
    // String page = attempt.getStream();
    WebElement message;
    //String markup;
    boolean getDataStart(){
	driver.navigate().refresh();
	message = getMessageGroup();
	//markup = getMarkup(message);
	newMessage = removeTimeStamp(message.getText());
	//System.out.println(newMessage);
	if(!(oldMessage.equals(newMessage))){
	    mes = getLastMessage(newMessage);
	    getCommand(mes);
	    if(command.size() == 0){
		command.add("null");
	    }
	    return true;
	}
	else{
	    return false;
	}
    }
    void getDataEnd(){
	message = getMessageGroup();
	//markup = getMarkup(message);
	newMessage = removeTimeStamp(message.getText());
	oldMessage = newMessage;
    }
    public boolean startup(){
	//if(!attempt.getConfig()){
        //System.out.println("You don't have an mnf.botto file. Please run java Botto to begin setup.");
        //kill();
        //return false;
	// }
	//if(!attempt.channelExists()){
	//  System.out.println("You did not set-up credentials for messenger in your mnf.botto file.");
	// kill();
	// return false;
	// }
	try{
      String messengerChannel = "https://mbasic.facebook.com";
      driver.get(messengerChannel);
      System.out.println("selectMode");
      Scanner sc = new Scanner(System.in);
      String selection = sc.nextLine();
      if(selection.equals("pacer")){
	  mode = new pacer(this);
      }
      else if(selection.equals("rpg")){
	  mode = new rpg(this);
      }
      else if(selection.equals("bump")){
	  mode = new bump(this);
      }
      else if(selection.equals("autoRespond")){
	  mode = new autoRespond(this);
      }
      else if(selection.equals("likes")){
	  mode = new likes(this);
      }
      else{
	  mode = new normal(this);
      }
      System.out.println(selection);
      oldMessage = removeTimeStamp(getMessageGroup().getText());
	}
	catch(Throwable e){
	    e.printStackTrace();
	    System.out.println("error");
	    //File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    //System.out.println(srcFile);
    try{
	//FileUtils.copyFile(srcFile, new File("screen.png"));
    }
    catch(Exception f){

    }
      driver.quit();
	    e.printStackTrace();
	}
	return true;
    }
    String mes;


    //NORMALY TRUE
    boolean REPLYMODE = true;
    //SWITCH TO FALSE IF SPAMMING SOMEONE
    boolean tick(){if(!REPLYMODE){
	    mode.tick();
	}
	else{
	    if(pause <= 0 && getState().equals("on")){
		pause = sleepTime;
		//driver.navigate().refresh();
		try{
		    
		    if(getDataStart()){
			System.out.println(getUserName2(newMessage));
			System.out.println(command);
			System.out.print('\n');
			mode.tick();
			getDataEnd();
			updateSleepCounter(true);
		    }
		    updateSleepCounter(false);
		}
		//System.out.println(driver.getPageSource());
		catch(StringIndexOutOfBoundsException e){
		    send("nice picture");
		}
		catch(Throwable e){
		    e.printStackTrace();
		    //driver.get("https://mbasic.facebook.com/messages/read/?fbid="+page+"&_rdr");
		    send("That's an error mate.");
		    try{
			TimeUnit.SECONDS.sleep(1);
		    }
		    catch(Exception q){
			q.printStackTrace();
		    }
		}
	    }
	    else{
		pause--;
	    }
	}
	return true;
}
}
class likes implements Mode{
    Messenger c;
    String targetName;
    int holder = 0;
    int year = 0;
    String[]years;
    public likes(Messenger a){
	c = a;
	c.REPLYMODE = false;
	System.out.println("enter target name:");
	Scanner sc = new Scanner(System.in);
	targetName = sc.nextLine();
	System.out.println("input years, seperate by comma");
	years = sc.nextLine().split(",");
    }
    public void tick(){
	try{
	    TimeUnit.SECONDS.sleep(1);
	}
	catch(Throwable e){
	    e.printStackTrace();
	    System.exit(0);
	}
	List<WebElement>posts = c.driver.findElements(By.cssSelector("div[role='article']"));
	for(int i = holder;i < posts.size();i++){
	    List<WebElement>names;
	    if(year != 0){
		names = posts.get(i).findElements(By.className("cd"));
	    }
	    else{
		names = posts.get(i).findElements(By.className("bx"));
	    }
	    if(year != 0){
		if(names.size() == 0){
		    names = posts.get(i).findElements(By.className("cf"));
		}
		if(names.size() == 0){
		    names = posts.get(i).findElements(By.className("ce"));
		}
	    }
	    if(names.size() == 0){
		names = posts.get(i).findElements(By.className("bx"));
	    }
	    if(names.size() == 0){
		names = posts.get(i).findElements(By.className("by"));
	    }
	    if(names.size() == 0){
		continue;
	    }
	    WebElement name = names.get(0);
	    if(name.findElement(By.cssSelector("a")).getText().equals(targetName)){
		List<WebElement>l;
		l = posts.get(i).findElements(By.cssSelector("span[id*='like']"));
		if(l.size() != 0){
		    List<WebElement> a;
		    a = l.get(0).findElements(By.cssSelector("a[href*='like']"));
		    if(a.size() != 0){
			a.get(0).click();
			holder = i + 1;
			try{
			    TimeUnit.SECONDS.sleep(2);
			}
			catch(Throwable e){
			    e.printStackTrace();
			    System.exit(0);
			}
		    }
		    return;
		    
		}
		
	    }
	}
	List<WebElement>a = c.driver.findElements(By.cssSelector("a"));
	for(int i = a.size() - 1;i >= 0; i--){
	    if(a.get(i).getText().equals("Show more")){
		a.get(i).click();
		try{
		    TimeUnit.SECONDS.sleep(2);
		}
		catch(Throwable e){
		    e.printStackTrace();
		    System.exit(0);
		}
		holder = 0;
		return;
	    }
	}
	if(year < years.length){
	    for(int i = a.size() - 1;i >= 0;i--){
		if(a.get(i).getText().equals(years[year])){
		    a.get(i).click();
		    try{
			TimeUnit.SECONDS.sleep(2);
		    }
		    catch(Throwable e){
			e.printStackTrace();
			System.exit(0);
		    }
		    holder = 0;
		    year++;
		    return;
		    
		}
	    }
	}
	System.out.println("error or end timeline");
	System.exit(0);
    }

}

class pacer implements Mode{
    Messenger c;
    String pace = "TheFitnessGramPacerTestisamultistageaerobiccapacitytestthatprogressivelygetsmoredifficultasitcontinues.The20meterpacertestwillbeginin30seconds.Lineupatthestart.Therunningspeedstartsslowly,butgetsfastereachminuteafteryouhearthissignal.";
    int index = 0;
    public pacer(Messenger a){
	c = a;
    }
    public void tick(){
	c.REPLYMODE = false;
	c.send(pace.substring(index++,index));
	try{
	    TimeUnit.SECONDS.sleep(3);
	}
	catch(Throwable e){
	    e.printStackTrace();
	    System.exit(0);
	}

    }
    
}
class autoRespond implements Mode{
    Messenger c;
    public autoRespond(Messenger a){
	c = a;
    }
    String messageToSend = "hello, this is an auto-respond message";
    String secondMessage = messageToSend;
    boolean responded = false;
    public void tick(){
	c.REPLYMODE = true;
	if(responded){
	    c.send(secondMessage);
	}
	else{
	    c.send(messageToSend);
	}
    }

}
class bump implements Mode{
    Messenger c;
    boolean responded= false;
    public bump(Messenger a){
	c = a;
    }
    public void tick(){
	c.REPLYMODE = false;
	if(responded){
	    System.exit(0);
	    return;
	}
	if(c.getDataStart()){
	    if(c.commandCheck("end",false,0,0)){
		responded = true;
	        String[] fullMessage = new String[]{"messange stream ended"};
		for(int i = 0; i < fullMessage.length;i++){
		    c.send(fullMessage[i]);
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("respond obtained:\n" + dateFormat.format(date).toString());
		System.exit(0);
		return;
	    }
	}
	c.getDataEnd();
	c.send("^ (type \"end\" to stop)");
	try{
	    TimeUnit.SECONDS.sleep(5);
	}
	catch(Throwable e){
	    e.printStackTrace();
	    System.exit(0);
	}
    }

}
interface Mode{
    public void tick();
}
class normal implements Mode{
    Messenger c;
    public normal(Messenger a){
	c = a;
    }
    public void tick(){
	if(c.mes.charAt(0) == '-'){
	   if(c.commandCheck("-hi",false,0,0)){
	       c.send("hi");
	   }
	}
	else{   
	}
    }
}
class Foe{
    static String name;
    float hp;
    float attack;
    rpg r;
    void inspect(){

    }
    void display(){

    }
    void turn(){

    }
    void takeDamage(){

    }
    Foe next;
}
class potato extends Foe{
    public potato(float hp,float attack,rpg r){
	this.hp = hp;this.attack = attack;this.r = r;
	next = null;
	name = "potato";
    }
    void turn(){
	r.output += "\npotato rolls towards you, dealing "+attack +" damage.\nits attack increased by 150%.";
	attack *= 2.5;
	r.hp -= attack;
	if(r.hp <= 0){
	    r.output += "\nyou were crushed by the potato.";
	    r.dead = true;
	}
    }
    void takeDamage(){
	if(hp > 0){
	    r.output += "\nsmells like french fries";
	}
	else{
	    r.output += "\nthe potato perished";
	    r.output += "\nScore:56 degrees Kelvin";
	    r.output += "\n!!!Edmond.exe crashed!!!";
	    r.win = true;
	}
    }
    void display(){
	r.output += "\npOtATo";
    }
    void inspect(){
	display();
	r.output += "\nhp: " + hp + "\nattack: " + attack + "\nyour spirit animal";
    }
}
class slime extends Foe{
    static String name = "slime";
    public slime(float hp,float attack,rpg r){
	this.hp = hp;this.attack = attack;this.r = r;
	next = new potato(100,50,r);
    }
    int turn = 0;
    void turn(){
	if(turn++ % 3 == 0){
	    r.output += "\nSlime sprays explosive diarrhea at you dealing 50 damage.";
	    r.hp -= 50;
	}
	else{
	    r.output += "\nSlime attacks,dealing " + attack + " damage.";
	    r.hp -= attack;
	}
	if(r.hp <= 0){
	    r.output += "\nyou died a slow painful death.";
	    r.dead = true;
	}
    }
    void takeDamage(){
	if(hp > 0){
	    r.output += "\na heavy stench fills the air.";
	}
	else{
	    r.output += "\n the slime is dead";
	    r.output += "\nScore: " + turn + "\nlower the better";
	    r.output += "\nyou encountered a POTATO!!!!";
	    r.win = true;
	}
    }
    void display(){
	r.output += "\n      :poop:";
    }
    void inspect(){
	display();
	r.output += "\nhp: " + hp + "\nattack: " + attack + "\nlooks like jelly.";
    }

}
class rpg extends normal{
    String menu = "0";
    boolean dead = false;
    boolean win = false;
    boolean start = true;
    static final String view = "0";
    static final String act = "2";
    static final String item = "3";
    public rpg(Messenger a){
	super(a);
    }
    float hp = 100;
    float attack = 10;
    Foe foe = new slime(100,10,this);
    String output = "";
    boolean foeTurn = false;
    public void tick(){
	//if(c.getUserName2(c.newMessage).equals("Edmond Wong")){
	//    return;
	//}
	if(start){
	    output = "you encountered a slime";
	    foe.display();
	    output += "\nhp: " + hp +"\nattack: " + attack + "\nwhat will you do?\n(1)attack,(2)act,(3),item.";
	    c.send(output);
	    start = false;
	    return;
	}
	if(dead){
	    return;
	}
	if(win && foe.next != null){
	    win = false;
	    foe = foe.next;
	}
	output = "";
	if(menu.equals(view)){
	    if(c.commandCheck("1",false,0,0)){
		foe.hp -= attack;
		output += "\nyou took a bite out of the "+foe.name+", dealing " + attack + " damage.";
		foe.takeDamage();
		foeTurn = true;
	    }
	    else if(c.commandCheck(act,false,0,0)){
		menu = act;
		output += "(1)inspect,(2)back.";
	    }
	    else if(c.commandCheck(item,false,0,0)){
		menu = item;
		output += "(1)health potion,(2)back.";
	    }
	}
	else if(menu.equals(act)){
	    if(c.commandCheck("1",false,0,0)){
		menu = view;
		foe.inspect();
		foeTurn = true;
	    }
	    else if(c.commandCheck("2",false,0,0)){
		menu = view;
		foe.display();
		output += "\nhp: " + hp +"\nattack: " + attack + "\nwhat will you do?\n(1)attack,(2)act,(3),item.";
		//foeTurn = true;
	    }
	}
	else if(menu.equals(item)){
	    if(c.commandCheck("1",false,0,0)){
		menu = view;
		hp *= 2;
		attack += 1;
		output += "\nyou drank your red bull mixed with meth\nand all your pain seemed to fade away.";
		foeTurn = true;
	    }
	    else if(c.commandCheck("2",false,0,0)){
		menu = view;
		foe.display();
		output += "\nhp: " + hp +"\nattack: " + attack + "\nwhat will you do?\n(1)attack,(2)act,(3),item.";
		//foeTurn = true;
	    }
	}
	if(!output.equals("")){
	    c.send(output);
        }
	if(dead){
	    return;
	}
	if(win && foe.next != null){
	    win = false;
	    foe = foe.next;
	}
	output = "";
	if(foeTurn){
	    foe.turn();
	    foeTurn = false;
	    c.send(output);
	    output = "";
	    foe.display();
	    output = "\nhp: " + hp +"\nattack: " + attack + "\nwhat will you do?\n(1)attack,(2)act,(3),item.";
	    c.send(output);
	}
	


    }
    
}
