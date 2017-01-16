
import Botto;
class PluginManager{
    private static int pluginNum = 0;
    static String errorMessage;
    static int getPluginNum(){
	return pluginNum;
    }
    private static Controller lastPlugin = new Controller();
    private static boolean pluginCheck(String extensionFileName){
	String template = Model.readFromFile("PluginState0/PluginTemplate.java");
	String newPluginFile = template.replace("PluginTemplate","Plugin" + (pluginNum + 2) + ".java").replace("PluginSomeNumber","Plugin" + (pluginNum + 3));
	String[] touch = {"touch", "Plugin" + (pluginNum + 2) + ".java"};
	terminalCommand.go(touch);
	Model.writeToFile(newPluginFile,"Plugin" + (pluginNum + 2) + ".java");
	String next = Model.readFromFile("Plugin" + (pluginNum + 1) + ".java");
	Model.writeToFile(next.replace("/*toBeReplaced","").replace("toBeReplaced*/",""),"Plugin" + (pluginNum + 1) + ".java");
	String[] command = {"javac",extensionFileName};
	String result = terminalCommand.go(command);
	if(result.equals("null")){
	    return true;
	}
	else{
	    errorMessage = result;
	    return false;//returns true if file successfully compiles and meets requirments
	}
    }
    static boolean extensionTypePlugin(int index,Controller toBeExtended,String extensionFileName){
	if(pluginCheck(extensionFileName)){
	    getController(toBeExtended.getIndex()).setState("off");
	    addController(lastPlugin.nextPlugin(index,toBeExtended.getIndex(),toBeExtended.driver));
	    lastPlugin = toBeExtended;
	    pluginNum++;
	    return true;
	}
	else{
	    return false;
	}
    }
}
