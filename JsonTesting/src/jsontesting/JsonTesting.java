package jsontesting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import org.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

        // Create a JButton for submitting data to JSON log file
        JButton log_button = new JButton("Log Data");
        log_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                
                File t = new File("saves/log.json");
                if(t.exists())
                {
                    System.out.println("Log file already exists");
                }else{
                    System.out.println("Log file not found");
                }
                

                
                
                String inputName = name.getText();
                String inputLevel = level.getText();
                String inputMoney = money.getText();
                String inputResources = resources.getText();
                
                JSONObject obj = new JSONObject();
                obj.put("username",inputName);
                obj.put("playerLvl",inputLevel);
                obj.put("moneyBal",inputMoney);
                obj.put("resources",inputResources);
                
                try(FileWriter file = new FileWriter("saves/log.json"))
                {
                    file.write(obj.toString());
                    file.flush();
                    System.out.println("Successfully wrote to log file");
                }catch (IOException f)  
                {
                    System.out.println("An error occurred in file writing");
                }
                
                               
                
                JOptionPane.showMessageDialog(null, "Data saved to log.JSON");
                
            }
        });
        panel.add(log_button);
        
        // Create a JButton for loading data from log.JSON file
        JButton load_button = new JButton("Load Existing Data");
        load_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent g) {
                
                
                File t = new File("saves/log.json");
                
                try(FileReader reader = new FileReader("saves/log.json"))
                {
                    JSONParser parser = new JSONParser();
                    JSONObject playerInfo = (JSONObject) parser.parse(reader);
                    //System.out.println(playerInfo);
                    name.setText((String) playerInfo.get("username"));
                    level.setText((String) playerInfo.get("playerLvl"));
                    money.setText((String) playerInfo.get("moneyBal"));
                    resources.setText((String) playerInfo.get("resources"));
                }catch(FileNotFoundException e)
                {
                    JOptionPane.showMessageDialog(null, "Existing log.JSON file not found");
                }catch(IOException e)
                {
                    System.out.println("IO Exception error caught");
                }catch(ParseException e)
                {
                    System.out.println("Parse Exception Caught");
                }
                    
                
                JOptionPane.showMessageDialog(null, "Data loaded from log.JSON");
                
            }
        });
        panel.add(load_button);
        

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
