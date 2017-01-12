import org.openqa.selenium.WebDriver;
class Plugin1 extends Controller{//can also extend discord and messenger
    //maybe able to extend other plugins
    private WebDriver driver;
    Plugin1(WebDriver driver,String tag){
	super(tag);
	this.driver = driver;
    }
    Plugin1(WebDriver driver){
	super("PluginNameGoesHere");
	this.driver = driver;
    }
    boolean startup(){
	//insert startup code here
	return true;
    }
    boolean tick(){
	//insert tick code here
	return true;
    }
    void runPluginDash(){
	try{
	    /*
	    if(commandCheck(String commandName,Boolean unlimitedInputs?,int minInputs,int maxInputs){
	    commandThatsSupposedToRun();
	    }
	    else if(commandCheck...........
	    you get the point
	    */
	}
	catch(Exception e){
	    makeErrorReport(e);
	}
    }
    void runPlugin(){
	try{
	    //same as runPluginDash() except this is for commands that start without a "-"
	}
	catch(Exception e){
	    makeErrorReport(e);
	}
    }
    /*
    Controller nextPlugin(WebDriver driver){
	Controller plugin = new Plugin2(driver);
	return plugin;
    }
    */
}
