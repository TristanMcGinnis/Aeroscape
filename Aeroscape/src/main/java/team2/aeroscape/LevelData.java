package team2.aeroscape;


//import org.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Tristan McGinnis
 */
public class LevelData {
    String name = "defaultPlayer";
    int lvl = 0;
    int difficulty = 0;
    int inventory[];
    int mapData[][];
    private ArrayList<Miner> miners;
    FireControl FC;
    
    /**
     * This constructor takes all relevant save information for logging/loading for persistence between sessions.
     * @param name The name of the save
     * @param lvl The current level of the player
     * @param difficulty The difficulty level of the save
     * @param inventory Array of inventory values. Each index represents a set type of "resource"
     * @param mapData 2D Array storing the state of each grid of the save state. Values represent grid-state (ex: 0 is empty, 1 is MachineX, 2 is MachineY).
     */
    public LevelData(String name, int lvl, int difficulty, int inventory[], int mapData[][])
    {
        this.name = name;
        this.lvl = lvl;
        this.difficulty = difficulty;
        this.inventory = inventory;
        this.mapData = mapData;
        
        this.mapData = new int[mapData.length][mapData[0].length];
        this.miners = new ArrayList<Miner>();
        this.FC = new FireControl();

        
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                this.mapData[i][j] = mapData[i][j];
            }
        }
    }
    
    /**
     * Creates save directory if it doesn't already exist.
     */
    public static void checkSaveDirectory()
    {
        new File("saves").mkdirs();
    }
    
    public void addMiner(Miner miner) {
        miners.add(miner);
    }

    public ArrayList<Miner> getMiners() {
        return miners;
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
        for(int val[] : inArray)
        {
            JSONArray rowArr = new JSONArray();
            for(int v : val)
            {
                rowArr.add(v);
            }
            outArray.add(rowArr);
        }
        
        return outArray;
    }
    
    public static int[] FromJsonArr(JSONArray inArray)
    {
        
        int outArray[] = new int[inArray.size()];
        for(int i = 0; i < inArray.size(); i++)
            {
                outArray[i] = ((Long)inArray.get(i)).intValue();                            
            }
        return outArray;
    }
    
    public static int[][] FromJson2DArr(JSONArray inArray)
    {
        int outArray[][] = new int[inArray.size()][((JSONArray) inArray.get(0)).size()];
        //System.out.println("arraySize: "+inArray.size());
        for(int i = 0; i < inArray.size(); i++)
        {
            JSONArray row = (JSONArray)inArray.get(i);
            //outArray[i] = row;
            for(int j = 0; j < row.size(); j++)
            {
                //System.out.println("Loc:"+i+","+j);
                outArray[i][j] = ((Long)row.get(j)).intValue();
            }
        }
        return outArray;
    }
    
    public static void logData(LevelData player)
    {
        //debug
        //System.out.println("Attempting to save data");
        
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
                    dataObj.put("mapData", ToJson2DArr(player.mapData));
                    
        //debug
        System.out.println("Attempting to write data to file");
        try(FileWriter file = new FileWriter(logFilePath))
                    {
                        file.write(dataObj.toString());
                        file.flush();
                        System.out.println("Successfully wrote to log file for " + player.name);
                        //System.out.println(logFilePath);
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
                        
                        //Values read as LONG from JSON must be converted back to Integers
                        int playerLvl = ((Long)playerInfo.get("playerLvl")).intValue();
                        int difficulty = ((Long)playerInfo.get("difficulty")).intValue();
                        
                        //Read & Convert the inventory array
                        JSONArray jsonInv = (JSONArray) playerInfo.get("inventory");
                        int inventory[];
                        inventory = FromJsonArr(jsonInv);
                        
                        //DEBUG
                        /*
                        System.out.println("Inventory:");
                        for(int v : inventory)
                        {
                            System.out.println(v);
                        }
                        */
                                              
                        
                        //Read & Convert the mapData array
                        JSONArray jsonLevelData = (JSONArray) playerInfo.get("mapData");
                        int mapData[][];
                        mapData = FromJson2DArr(jsonLevelData);
                    
                        LevelData tempData = new LevelData(name, playerLvl, difficulty, inventory, mapData);
                        //LevelData tempData = new LevelData(name, playerLvl, difficulty, inventory, mapData);                      
                        //System.out.println(playerInfo);
                        //LevelData tempPlayer = new LevelData(name, playerLvl, difficulty, inventory, mapData);
                        return tempData;
                        //return null;
                    }catch(FileNotFoundException e)
                    {
                        //JOptionPane.showMessageDialog(null, "Existing log.JSON file not found");
                        //System.out.println("Existing log.JSON file not found");
                        System.out.println(e);
                        return null;
                    }catch(IOException e)
                    {
                        //System.out.println("IO Exception error caught");
                        System.out.println(e);
                        return null;
                    }catch(ParseException e)
                    {
                        //System.out.println("Parse Exception Caught");
                        System.out.println(e);
                        return null;
                    }
    }
    
    /*
    //Example Main function with implementation test data
    public static void main(String[] args)
    {
        System.out.println("LevelData log Test Start");
        String saveName = "TestFullSave";
        int lvl = 20;
        int diff = 3;
        int inventory[] = new int[]{0, 5, 10, 15};
        int mapData[][]= new int[][]{{0, 1, 0, 1},
                                        {1, 1, 0, 0},
                                        {1, 0, 1, 0},
                                        {0, 0, 0, 1}};
        LevelData saveTest = new LevelData(saveName, lvl, diff, inventory, mapData);
        logData(saveTest);      
        //read data
        System.out.println("LevelData read Test Start");
        lvl = 0;
        diff = 0;
        inventory = new int[inventory.length];
        mapData = new int[mapData.length][Array.getLength(mapData[0])];
        System.out.println("Done Resetting Values For Test");
        LevelData readTest = loadData(saveName);
        
        //print read data
        System.out.println("READ RESULTS");
        System.out.println("Name: "+saveName);
        System.out.println("Level: "+readTest.lvl);
        System.out.println("Difficulty: " + readTest.difficulty);
        System.out.print("Inventory: {");
            for(int val: readTest.inventory)
            {
                System.out.print(val+ " ");
            }
        System.out.println("}");
        System.out.println("Level Data: ");
            for(int row[]: readTest.mapData)
            {
                System.out.print("{");
                for(int val: row)
                {
                    System.out.print(val + " ");
                }
                System.out.println("}");
            }
        
        //System.out.println("LevelData: "+readTest.mapData);
        
    }
    */
}

