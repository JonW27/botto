import org.openqa.selenium.WebDriver;
class Plugin0 extends Controller{//can also extend discord and messenger
    //maybe able to extend other plugins
    private WebDriver driver;
    Plugin0(WebDriver driver,String tag){
	super(tag);
	this.driver = driver;
    }
    public boolean startup(){
	//insert startup code here
	return true;
    }
    public boolean tick(){
	//inseart tick code here
	return true;
    }
    /*
    Controller nextPlugin(WebDriver driver,String tag){
	Controller plugin = new Plugin1(driver,tag);
	return plugin;
    }
    */
    //^^^ will be commented out once plugin0 is created
}
