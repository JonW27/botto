import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder;
public class command{
    public static void main(String[]args){
	try{
	    List<String> command = new ArrayList<String>();
	    command.add("python"); // command name
      command.add("-V");
	    //command.add("hiThere.txt"); // optional args added as separate list items
	    ProcessBuilder pb = new ProcessBuilder (command);
	    Process p = pb.start();
	    p.waitFor();
	}
	catch(Throwable e){
	    e.printStackTrace();
	}
    }
}
