package team2.aeroscape;

import javax.swing.*;

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
                        //JOptionPane.showMessageDialog(null, "Existing log.JSON file not found");
                        System.out.println("Existing log.JSON file not found");
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
    
}
