package team2.aeroscape;

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
public class PlayerData {
    String name = "defaultPlayer";
    int lvl = 0;
    int bal = 0;
    int resourceA = 0;
    
    public PlayerData(String name, int lvl, int bal, int resourceA)
    {
        this.name = name;
        this.lvl = lvl;
        this.bal = bal;
        this.resourceA = resourceA;
    }
    
    
    public static void logData(PlayerData player)
    {
        String logFilePath = "saves/"+player.name + "Log.json";
        File t = new File(logFilePath);
                    if(t.exists())
                    {
                        System.out.println("Log file already exists for "+ player.name);
                    }else{
                        System.out.println("Log file not found for " + player.name + ". Creating one.");
                    }
                    
        JSONObject dataObj = new JSONObject();
                    dataObj.put("playerName",player.name);
                    dataObj.put("playerLvl",player.lvl);
                    dataObj.put("moneyBal",player.bal);
                    dataObj.put("resourceA",player.resourceA);
                    
        try(FileWriter file = new FileWriter(logFilePath))
                    {
                        file.write(dataObj.toString());
                        file.flush();
                        System.out.println("Successfully wrote to log file for " + player.name);
                        System.out.println(logFilePath);
                    }catch (IOException f)  
                    {
                        System.out.println("IOException error occurred in file writing");
                        System.out.println(f);
                    }

    }
    
    public static PlayerData loadData(String name)
    {
        String logFilePath = "saves/"+name+"Log.json";
        
        
        File t = new File(logFilePath);

                    try(FileReader reader = new FileReader(logFilePath))
                    {
                        JSONParser parser = new JSONParser();
                        JSONObject playerInfo = (JSONObject) parser.parse(reader);
                        int playerLvl = ((Long)playerInfo.get("playerLvl")).intValue();
                        int playerMoney = ((Long)playerInfo.get("moneyBal")).intValue();
                        int resourceA = ((Long)playerInfo.get("resourceA")).intValue();
                        //System.out.println(playerInfo);
                        PlayerData tempPlayer = new PlayerData(name, playerLvl, playerMoney, resourceA);
                        return tempPlayer;
                    }catch(FileNotFoundException e)
                    {
                        JOptionPane.showMessageDialog(null, "Existing log.JSON file not found");
                        return null;
                    }catch(IOException e)
                    {
                        System.out.println("IO Exception error caught");
                        return null;
                    }catch(ParseException e)
                    {
                        System.out.println("Parse Exception Caught");
                        return null;
                    }
    }
    
    //This main function exists for testing save/load procedures
    public static void main(String[] args) {
            // Create a new JFrame
            JFrame frame = new JFrame();

            // Create a JPanel for the text box and button
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            // Create Text Field componenets for entering a number
            JTextField name = new JTextField(10);
            panel.add(name);
            JTextField level = new JTextField(5);
            panel.add(level);
            JTextField money = new JTextField(5);
            panel.add(money);
            JTextField resources = new JTextField(5);
            panel.add(resources);
            name.setToolTipText("Enter Player Name");
            level.setToolTipText("Enter Player Level");
            money.setToolTipText("Enter Money Balance");
            resources.setToolTipText("Enter Resources Count");        

            
            
            // Create a JButton for logging data to JSON log file
            JButton log_button = new JButton("Log Data");
            log_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String inputName = name.getText();
                    int inputLevel = Integer.parseInt(level.getText());
                    int inputMoney = Integer.parseInt(money.getText());
                    int inputResources = Integer.parseInt(resources.getText());
                    
                    PlayerData newPlayer = new PlayerData(inputName, inputLevel, inputMoney, inputResources);
                    logData(newPlayer);
                    
                    String dataLogMsg = "Data saved to log for "+name.getText();
                    JOptionPane.showMessageDialog(null, dataLogMsg);

                }
            });
            panel.add(log_button);

            // Create a JButton for loading data from JSON log file
            JButton load_button = new JButton("Load Existing Data");
            load_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent g) {


                    PlayerData loadedPlayer = loadData(name.getText());
                    if(loadedPlayer != null)
                    {
                        name.setText((String) loadedPlayer.name);
                        level.setText(Integer.toString(loadedPlayer.lvl));
                        money.setText(Integer.toString(loadedPlayer.bal));
                        resources.setText(Integer.toString(loadedPlayer.resourceA));
                        JOptionPane.showMessageDialog(null, "Data loaded from log of "+loadedPlayer.name);
                    }else{
                        JOptionPane.showMessageDialog(null, "Data couldn't be loaded fro log of "+name);
                    }
                }
            });
            panel.add(load_button);


            // Add the panel to the frame
            frame.add(panel);

            // Set the frame's properties
            frame.setTitle("Player Data Entry");
            frame.setSize(300, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            // Show the frame
            frame.setVisible(true);
        }
   

    

    
}
