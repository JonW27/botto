
import org.openqa.selenium.WebDriver;
class Plugin2.java extends Controller{/*can also extend Discord and Messenger
    //maybe able to extend other plugins
    //When making methods, you might want to add a _ at the start of the name to avoid overridding certain methods.*/
    Plugin2.java(int Index,WebDriver driver){
	super(Index,"PluginNameGoesHere",driver);
    }
    Plugin2.java(int Index, int parentIndex, WebDriver driver){
	super(Index,parentIndex,"PluginNameGoesHere",driver);
    }
    boolean startup(){
	/*insert startup code here
	//startup code dosen't run in extensionTypePlugins
	//a startup code is still needed however since extensionTypePlugins eventually be come permanentTypePlugins

	//return super.startup();*/
	return true;
    }
    boolean tick(){
	/*insert tick code here
	//return super.tick();*/
	return true;
    }
    void runPluginDash(){
	try{
	    /*
	    if(commandCheck(String commandName,Boolean unlimitedInputs?,int minInputs,int maxInputs)){
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
	    /*same as runPluginDash() except this is for commands that start without a "-"*/
	}
	catch(Exception e){
	    makeErrorReport(e);
	}
    }
    /*toBeReplaced
    Controller nextPlugin(int index,WebDriver driver){
	Controller plugin = new Plugin3(index,driver);
	return plugin;
    }
    //only used for extension type plugins
    
    Controller nextPlugin(int index,int parentIndex,WebDriver driver){
	Controller plugin = new Plugin3(index,parentIndex,driver);
	return plugin;
    }
    toBeReplaced*/
}
