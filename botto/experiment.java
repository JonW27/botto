import java.util.*;
import java.io.*;
public class experiment{
    public static void main(String[]args){
	ArrayList<experimentPart2> test = new ArrayList<experimentPart2>();
	test.add(new experimentPart2());
	Scanner reader = new Scanner(System.in);
	String result = reader.nextLine();
	test.add(new experimentPart2());
	test.get(0).go();
	test.get(1).go();
	
    }
}
