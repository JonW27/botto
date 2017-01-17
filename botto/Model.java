//place data and file reading functions here
import java.io.*;
import java.util.*;
import org.apache.commons.io.FileUtils;
import static java.lang.System.out;
class Model{
  String channel;
  String username;
  String password;
  String fullPassword;
  File mnf = new File("mnf.botto");
  Boolean channelExists = false;
  Model(String channel){
    this.channel = channel;
    readMNF();
  }
  boolean getChannel(){
    return channelExists;
  }
  String getUsername(){
    return username;
  }
  String getPassword(){
    return password;
  }
  Boolean getConfig(){
    return mnf.exists() && !mnf.isDirectory();
  }
  private void readMNF(){
    File mnf = new File("mnf.botto");
    Scanner in;
    try{
      in = new Scanner(mnf);
    }
    catch(FileNotFoundException d){
      return;
    }
    String hashed = in.nextLine();
    Scanner reader = new Scanner(System.in);
    System.out.println("Password:");
    String pass = reader.nextLine();
    if(!BCrypt.checkpw(pass, hashed)){
      System.out.println("Not correct password");
      return;
    }
    while(in.hasNext()){
        String line = in.next();
        String[] creds = line.split(":");
        if(creds[0].equals(channel)){
          channelExists = true;
          String key = pass;
          String salt = pass.substring(0,2);
          byte[] iv = new byte[16];
          Encryption encryption = Encryption.getDefault(key, salt, iv);
          username = encryption.decryptOrNull(creds[1]);
          password = encryption.decryptOrNull(creds[2]);
        }
    }
  }
  static boolean yesNoPrompt(String prompt){
    Scanner reader = new Scanner(System.in);
    System.out.println(prompt+" (y/n)");
    String result = reader.nextLine();
    if(result.equals("y")){
      return true;
    }
    else if(result.equals("n")){
      return false;
    }
    else{
      System.out.println("Not in proper format... please try again.");
      return yesNoPrompt(prompt);
    }
  }
  static void addWhichOne(Boolean discord, Boolean messenger, Boolean slack, PrintWriter writer, String secKey){
    Scanner reader = new Scanner(System.in);
    out.println("Which credentials would you like to add? "+" (discord/messenger/slack)");
    String result = reader.nextLine();
    String record = "";
    if(result.equals("discord") && !discord){
      record += "discord:";
      discord = true;
    }
    else if(result.equals("messenger") && !messenger){
      record += "messenger:";
      messenger = true;
    }
    else if(result.equals("slack") && !slack){
      record += "slack:";
      slack = true;
    }
    else{
      out.println("Not recognized... or already have credentials for channel.");
      addWhichOne(discord, messenger, slack, writer, secKey);
      return;
    }
    String key = secKey;
    String salt = secKey.substring(0,2);
    byte[] iv = new byte[16];
    Encryption encryption = Encryption.getDefault(key, salt, iv);
    Scanner reader2 = new Scanner(System.in);
    System.out.println("Username of account:");
    result += ":";
    result += encryption.encryptOrNull(reader.nextLine());
    result = result.substring(0,result.length()-1);
    result += ":";
    Scanner reader3 = new Scanner(System.in);
    out.println("Password of account:");
    result += encryption.encryptOrNull(reader.nextLine());
    result = result.substring(0,result.length()-1);
    writer.println(result);
    if(yesNoPrompt("Would you like to repeat for a different channel?")){
      addWhichOne(discord, messenger, slack, writer, secKey);
    }
  }
  public static String setPass(PrintWriter writer){
    String bob = sanitizedPass();
    writer.println(BCrypt.hashpw(bob, BCrypt.gensalt(12)));
    return bob;
  }
  public static String sanitizedPass(){
    Scanner reader = new Scanner(System.in);
    System.out.println("Set password for program:");
    String output = reader.nextLine();
    if(output.length() < 6){
      System.out.println("Password too short! Must be 6 characters at minimum.");
      return sanitizedPass();
    }
    return output;
  }
    static String userInput(){
	Scanner reader = new Scanner(System.in);
	String result = reader.nextLine();
	return result;
    }
    static boolean writeToFile(String input,String fileName){
	try{
	    File f = new File(fileName);
	    FileUtils.writeStringToFile(f,input,"UTF-8");
	}catch(IOException q){
	    q.printStackTrace();
	    return false;
	}
	return true;
    }
    static String readFromFile(String fileName){
	File x = new File(fileName);
	try{
	    Scanner y = new Scanner(x);
	    
	    String line = "";
	    while(y.hasNext()){
		line += y.next();
	    }
	    return line;
	}
	catch(FileNotFoundException f){
	    f.printStackTrace();
	    return "failed";
	}
	
    }
    public static void checkForSettings(){
	File metadata = new File("mnf.botto");
	if(!metadata.exists()){
	    out.println("A mnf.botto file was not detected... is this your first time setting up botto?");
	    if(yesNoPrompt("Would you like me to create a mnf.botto file for you?")){
		try{
		    PrintWriter writer = new PrintWriter("mnf.botto", "UTF-8");
		    String secKey = setPass(writer);
          addWhichOne(false, false, false, writer, secKey);
          writer.close();
		}
		catch(IOException e){
		    e.printStackTrace();
		}
		
	    }
	}
    }
}
