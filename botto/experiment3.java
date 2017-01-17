import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder;
public class experiment3 {
    public static void main(String args[]) throws InterruptedException {
	
        List<String> commands = new ArrayList<String>();
	//for(int i = 0; i < args.length; i++){
	    //commands.add(args[i]);
	//}
	commands.add("python");
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true);
        try {
	    
            Process prs = pb.start();
            Thread inThread = new Thread(new In(prs.getInputStream()));
            inThread.start();
            Thread.sleep(2000);
            OutputStream writeTo = prs.getOutputStream();
            
	    writeTo.write("print\"hi\"\n".getBytes());
            writeTo.flush();
            writeTo.close();
	    
	    
	    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class In implements Runnable {
    private InputStream is;

    public In(InputStream is) {
        this.is = is;
    }
    
    @Override
    public void run() {
        try {
            BufferedReader reader =
		new BufferedReader(new InputStreamReader(is));
	    String line = "";
	    while((line = reader.readLine()) != null) {
		System.out.println(line);
	    }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	
    }
}
