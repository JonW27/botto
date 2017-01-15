
import Botto;
class PluginManager{
    private static int pluginNum = 0;
    private static Controller lastPlugin = new Controller();
    private static boolean pluginCheck(){
	//uses pluginNum and terminal commands
	//also makes new plugin file
	return false;//returns true if file successfully compiles and meets requirments
    }
    static void extensionTypePlugin(int index,Controller toBeExtended){
	if(pluginCheck()){
	    getController(toBeExtended.getParentIndex()).setState("off");
	    addController(lastPlugin.nextPlugin(index,toBeExtended.getParentIndex(),toBeExtended.driver));
	}
    }
}
