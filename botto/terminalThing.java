import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class terminalThing{

    public static void main(String[] args) {
	try{
	    String command = "java Botto discord";

	    Process proc = Runtime.getRuntime().exec(command);

	    // Read the output

	    BufferedReader reader =
		new BufferedReader(new InputStreamReader(proc.getInputStream()));
    BufferedWriter reader1 = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));

	    String line = "";
      String line1 = "";
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
