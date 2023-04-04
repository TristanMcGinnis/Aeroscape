package team2.aeroscape;


//import org.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.lang.reflect.Array;

/**
 *
 * @author Tristan McGinnis
 */
public class LevelData {
    String name = "defaultPlayer";
    int lvl = 0;
    int difficulty = 0;
    int inventory[];
    int levelData[];
    
    /**
     * This constructor takes all relevant save information for logging/loading for persistence between sessions.
     * @param name The name of the save
     * @param lvl The current level of the player
     * @param difficulty The difficulty level of the save
     * @param inventory Array of inventory values. Each index represents a set type of "resource"
     * @param levelData 2D Array storing the state of each grid of the save state. Values represent grid-state (ex: 0 is empty, 1 is MachineX, 2 is MachineY).
     */
    public LevelData(String name, int lvl, int difficulty, int inventory[], int levelData[])
    {
        this.name = name;
        this.lvl = lvl;
        this.difficulty = difficulty;
        this.inventory = inventory;
        this.levelData = levelData;       
    }
    
    /**
     * Creates save directory if it doesn't already exist.
     */
    public static void checkSaveDirectory()
    {
        new File("saves").mkdirs();
    }
    
    
    public static JSONArray ToJsonArr(int inArray[])
    {
        JSONArray outArray = new JSONArray();
        for(int i=0; i < inArray.length; i++)
        {
            outArray.add(inArray[i]);
        }
                
        return outArray;
    }

    /**
     *
     * @param inArray
     * @return
     */
    public static JSONArray ToJson2DArr(int inArray[][])
    {
        JSONArray outArray = new JSONArray();
        JSONArray arrayOne = new JSONArray();
        JSONArray arrayTwo = new JSONArray();
        for(int i=0; i < Array.getLength(inArray[0]); i++)
        {
            arrayOne.add(inArray[0][i]);
        }
        for(int j=0; j < Array.getLength(inArray[0][0]); j++)
        {
            outArray.add(inArray[1][j]);
        }
        outArray.add(arrayOne);
        outArray.add(arrayTwo);
        
        return outArray;
    }
    
    public static void logData(LevelData player)
    {
        //debug
        System.out.println("Attempting to save data");
        
        checkSaveDirectory();
          
        
        String logFilePath = "saves/"+player.name + "Log.json";
        File t = new File(logFilePath);
                    if(t.exists())
                    {
                        System.out.println("Log file already exists for "+ player.name);
                    }else{
                        System.out.println("Log file not found for " + player.name + ". Creating one.");
                    }
                    
        JSONObject dataObj = new JSONObject();
                    dataObj.put("saveName",player.name);
                    dataObj.put("playerLvl",player.lvl);
                    dataObj.put("difficulty",player.difficulty);
                    dataObj.put("inventory",ToJsonArr(player.inventory));
                    dataObj.put("levelData", ToJsonArr(player.levelData));
                    
        //debug
        System.out.println("Attempting to write data to file");
        try(FileWriter file = new FileWriter(logFilePath))
                    {
                        file.write(dataObj.toString());
                        file.flush();
                        System.out.println("Successfully wrote to log file for " + player.name);
                        System.out.println(logFilePath);
                    }catch (IOException f)  
                    {
                        //System.out.println("IOException error occurred in file writing");
                        System.out.println(f);
                    }
    }
    
    public static LevelData loadData(String name)
    {
        String logFilePath = "saves/"+name+"Log.json";
        
        checkSaveDirectory();
        
        File t = new File(logFilePath);
        
                    try(FileReader reader = new FileReader(logFilePath))
                    {
                        JSONParser parser = new JSONParser();
                        JSONObject playerInfo = (JSONObject) parser.parse(reader);
                        int playerLvl = ((Integer)playerInfo.get("playerLvl"));
                        int difficulty = ((Integer)playerInfo.get("difficulty"));
                        
                        JSONArray jsonInv = (JSONArray) playerInfo.get("inventory");
                        int inventory[];
                        inventory = new int[Array.getLength(jsonInv)];
                        for(int i = 0; i < Array.getLength(jsonInv); i++)
                        {
                            inventory[i] = (Integer) jsonInv.get(i);                            
                        }
                        
                        JSONArray levelData = (JSONArray) playerInfo.get("levelData");
                        
                                                
                        
                        //System.out.println(playerInfo);
                        //LevelData tempPlayer = new LevelData(name, playerLvl, difficulty, inventory, levelData);
                        //return tempPlayer;
                        return null;
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
    
    
    public static void main(String[] args)
    {
        System.out.println("LevelData Test Start");
        String saveName = "TestFullSave";
        int lvl = 20;
        int diff = 3;
        int inventory[] = new int[]{0, 5, 10, 15};
        int levelData[]= new int[]{0, 1, 0, 0, 1, 0};
        LevelData saveTest = new LevelData(saveName, lvl, diff, inventory, levelData);
        logData(saveTest);      
        
    }
}

