import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder;
class terminalCommand {
    static String go(String a[]) throws InterruptedException {

        List<String> commands = new ArrayList<String>();
	for(int i = 0; i < a.length; i++){
	    commands.add(a[i]);
	}
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true);
        try {

            Process p = pb.start();
	    InputStream is = p.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    /*
	    OutputStream writeTo = p.getOutputStream();
            writeTo.write("n\n".getBytes());
            writeTo.flush();
            writeTo.close();
	    */
	    String result = "";
	    if((line = br.readLine()).equals(null)){
		result += "null";
	    }
	    else{
		result += line;
	    }
	    while ((line = br.readLine()) != null) {
		result += line;
	    }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
	    return "failed";
        }
    }
}
