
import org.openqa.selenium.WebDriver;
class PluginTemplate extends Discord{//can also extend Discord and Messenger
    //maybe able to extend other plugins
    //When making methods, you might want to add a _ at the start of the name to avoid overridding certain methods.
    PluginTemplate(int Index,WebDriver driver){
	super(Index,"shouldWorkPlugin",driver);
    }
    PluginTemplate(int Index, int parentIndex_, WebDriver driver){
	super(Index,parentIndex_,"shouldWorkPlugin",driver);
    }
    boolean startup(){
	//insert startup code here
	//startup code dosen't run in extensionTypePlugins
	//a startup code is still needed however since extensionTypePlugins eventually be come permanentTypePlugins

	//return super.startup();
	return true;
    }
    boolean tick(){
	//insert tick code here
	//return super.tick();
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
	    if(commandCheck("passed",false,0,5)){
            	send("it worked! :D");
	    }
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
    /*toBeReplaced
    Controller nextPlugin(int index,WebDriver driver){
	Controller plugin = new PluginSomeNumber(index,driver);
	return plugin;
    }
    //only used for extension type plugins
    
    Controller nextPlugin(int index,int parentIndex,WebDriver driver){
	Controller plugin = new PluginSomeNumber(index,parentIndex,driver);
	return plugin;
    }
    toBeReplaced*/
}
