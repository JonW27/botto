import java.util.*;
import java.io.*;
public class experiment{
    public static void main(String[]args){
	Scanner reader = new Scanner(System.in);
	String result = reader.nextLine();
	if (result.equals("y")){
	    experimentPart2 x = new experimentPart2();
	    System.out.println(experimentPart2.go());
	}
    }
}
