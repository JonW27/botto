import java.io.BufferedReader;
import java.io.InputStreamReader;

public class terminalThing{

    public static void main(String[] args) {
	try{
	    String[] command = {"java Botto"};

	    Process proc = Runtime.getRuntime().exec(command);

	    // Read the output

	    BufferedReader reader =
		new BufferedReader(new InputStreamReader(proc.getInputStream()));

	    String line = "";
	    while((line = reader.readLine()) != null) {
		System.out.print(line + "\n");
	    }
	    proc.waitFor();
	}
	catch(Throwable e){
	    e.printStackTrace();
	}
    }
}
