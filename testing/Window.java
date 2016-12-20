import javax.swing.*;
import java.awt.*;
public class Window extends JFrame {
 private Container pane;

 private JButton b;
 private JLabel l;
 private JTextField t;
 private JCheckBox c;
 
  //CONSTRUCTOR SETS EVERYTHING UP
  public Window() {
     this.setTitle("My first GUI");
     this.setSize(600,400);
     this.setLocation(100,100);
     this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
     pane = this.getContentPane();
     pane.setLayout(new FlowLayout());  //NOTE!! THIS CAN BE CHANGED (see below)
    
     b = new JButton("Do Nothing");
     l = new JLabel("This is AWESOME! (lies)",null,JLabel.CENTER);
     t = new JTextField(12);
     c = new JCheckBox("Overclock!!!");
     pane.add(l);
     pane.add(b);
     pane.add(t);
     pane.add(c);
 }

  //MAIN JUST INSTANTIATES + MAKE VISIBLE
  public static void main(String[] args) {
     Window g = new Window();
     g.setVisible(true);
  }
}
