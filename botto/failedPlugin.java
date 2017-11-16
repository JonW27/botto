import org.openqa.selenium.WebDriver;
class Plugin0 extends Discord{//can also extend discord and messenger
    //maybe able to extend other plugins
    //When making methods, you might want to add a _ at the start of the name to avoid overridding certain methods.
    Plugin0(int Index,WebDriver driver){
    super(Index,"TestPlugin",driver);
    }
    Plugin0(int Index, int parentIndex, WebDriver driver){
    super(Index,parentIndex,"TestPlugin",driver);
    }
    boolean startup(){
    //insert startup code here
    //startup code dosen't run in extensionTypePlugins
    //a startup code is still needed however since extensionTypePlugins eventually be come permanentTypePlugins

    return super.startup();
    }
    boolean tick(){
    //insert tick code here
    return super.tick();
    }
    void _1PluginTest(){
    for(int i = 0;i < command.size();i++){
        send(command.get(i));
    }
    }
    void runPluginDash(){
    try{
        if(commandCheck("-PluginTest",true,0,0)){
        _1PluginTest();
        //same as runPluginDash() except this is for commands that start without a "-"
        }
    }
    catch(Exception e){
        makeErrorReport(e);
    }
    }
    void _PluginTest(){
    send("success!!! :D");
    }
    void runPlugin(){
    try{
        if(commandCheck("PluginTest",false,0,0)){
        _PluginTest();
        //same as runPluginDash() except this is for commands that start without a "-"
        }
    }
    catch(Exception e){
        makeErrorReport(e);
    }
    }
      Controller nextPlugin(int index,WebDriver driver){
      Controller plugin = new Plugin1(index,driver);
      return plugin;
      }
      //only used for extension type plugins

      Controller nextPlugin(int index,int parentIndex,WebDriver driver){
      Controller plugin = new Plugin1(index,parentIndex,driver);
      return plugin;
      }
}