//place data and file reading functions here
import java.io.*;
import java.util.*;
import static java.lang.System.out;
class Model{
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
  static void addWhichOne(Boolean discord, Boolean messenger, Boolean slack, PrintWriter writer){
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
      addWhichOne(discord, messenger, slack, writer);
      return;
    }
    Scanner reader2 = new Scanner(System.in);
    System.out.println("Username of account:");
    result += ":";
    result += reader.nextLine();
    result += ":";
    Scanner reader3 = new Scanner(System.in);
    out.println("Password of account:");
    result += reader.nextLine();
    writer.println(result);
    if(yesNoPrompt("Would you like to repeat for a different channel?")){
      addWhichOne(discord, messenger, slack, writer);
    }
  }
    static String userInput(){
	Scanner reader = new Scanner(System.in);
	String result = reader.nextLine();
	return result;
    }
    static boolean writeToFile(String input,String fileName){
	Scanner reader = new Scanner(System.in);
	PrintWriter writer;
	try{
	    writer = new PrintWriter(fileName, "UTF-8");
	}catch(IOException q){
	    q.printStackTrace();
	    return false;
	}
	writer.println(reader.nextLine());
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
  static void checkForSettings(){
    File metadata = new File("mnf.botto");
    if(!metadata.exists()){
      out.println("A mnf.botto file was not detected... is this your first time setting up botto?");
      if(yesNoPrompt("Would you like me to create a mnf.botto file for you?")){
        try{
          PrintWriter writer = new PrintWriter("mnf.botto", "UTF-8");
          addWhichOne(false, false, false, writer);
          writer.close();
        }
        catch(IOException e){
          e.printStackTrace();
        }

      }
    }
  }
}
