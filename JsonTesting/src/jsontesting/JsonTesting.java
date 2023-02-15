package jsontesting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Tristan McGinnis
 */
public class JsonTesting {


    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
        // Create a new JFrame
        JFrame frame = new JFrame();
        
        // Create a JPanel for the text box and button
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Create a JTextField for entering a number
        JTextField name = new JTextField(10);
        panel.add(name);
        JTextField level = new JTextField(5);
        panel.add(level);
        JTextField money = new JTextField(5);
        panel.add(money);
        JTextField resources = new JTextField(5);
        panel.add(resources);
        
        //name.setText("Name");
        //level.setText("Lvl");
        //money.setText("Money");
        //resources.setText("Resources");
        name.setToolTipText("Enter Player Name");
        level.setToolTipText("Enter Player Level");
        money.setToolTipText("Enter Money Balance");
        resources.setToolTipText("Enter Resources Count");        

        // Create a JButton for determining odd/even
        JButton button = new JButton("Check");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputName = name.getText();
                String inputLevel = level.getText();
                String inputMoney = money.getText();
                String inputResources = resources.getText();
                
                try {
                    int num = Integer.parseInt(inputLevel);
                    if (num % 2 == 0) {
                        JOptionPane.showMessageDialog(null, "The number is even.");
                    } else {
                        JOptionPane.showMessageDialog(null, "The number is odd.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                }
                
            }
        });
        panel.add(button);

        // Add the panel to the frame
        frame.add(panel);

        // Set the frame's properties
        frame.setTitle("My Blank Window");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        // Show the frame
        frame.setVisible(true);
    }
   

    
}
