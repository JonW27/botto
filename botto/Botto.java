import java.io.*;

public class Botto{
  String[] cmd;
  public Botto(String... args){
    cmd = args;
  }
  public static void main(String[] args){
    Botto commence = new Botto(args);
  }
}
