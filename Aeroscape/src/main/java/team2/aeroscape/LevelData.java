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
 * LevelData object used for persistence between sessions
 * 
 * A <code>LevelData</code> object contains a variety of information necessary to continue
 * play upon relaunching the game such as player inventory and map data.
 * 
 * @author Tristan McGinnis
 * @version 1.0 April 18, 2023
 */
public class LevelData {
    String name = "defaultPlayer";
    int lvl = 0;
    int difficulty = 0;
    int inventory[] = new int[10];
    int mapData[][];
    ArrayList<Miner> miners;    //Not Persistent
    ArrayList<Smelter> smelters;    //Not Persistent
    ArrayList<SAM_PLATFORM> samPlatforms; //Not Persistent
    FireControl FC;     //Not Persistent
    EnemyHandler EH;    //Not Persistent
    
    /**
     * This constructor takes all relevant save information for logging/loading for persistence between sessions.
     * @param name The name of the save
     * @param lvl The current level of the player (unused)
     * @param difficulty The difficulty level of the save (unused)
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
        this.miners = new ArrayList<Miner>();   //Not Persistent
        this.smelters = new ArrayList<Smelter>();   //Not Persistent
        this.samPlatforms = new ArrayList<SAM_PLATFORM>(); //Not Persistent
        this.FC = new FireControl();    //Not Persistent
        this.EH = new EnemyHandler(difficulty, 1000); //Not Persistent

        this.mapData = mapData;
    }
    
    
    public void updateInventory(Inventory inv)
    {
        this.inventory[0] = inv.getCoal();
        this.inventory[1] = inv.getCopper();
        this.inventory[2] = inv.getIron();
        this.inventory[3] = inv.getGold();
        this.inventory[4] = inv.getCopperIngot();
        this.inventory[5] = inv.getIronIngot();
        this.inventory[6] = inv.getGoldIngot();
    }
    
    public void updateMapData(Tile[][] tiles)
    {
        mapData = tiles2MapData(tiles);
    }
    
    /**
     * This constructor takes all relevant save information for logging/loading for persistence between sessions.
     * @param name The name of the save
     * @param inventory Array of inventory values. Each index represents a set type of "resource"
     * @param mapData 2D Array storing the state of each grid of the save state. Values represent grid-state (ex: 0 is empty, 1 is MachineX, 2 is MachineY).
     */ 
    public LevelData(String name, int inventory[], int mapData[][])
    {
        this.name = name;
        this.lvl = 0;
        this.difficulty = 0;
        this.inventory = inventory;
        this.mapData = mapData;
        
        this.mapData = new int[mapData.length][mapData[0].length];
        this.miners = new ArrayList<Miner>();   //Not Persistent
        this.FC = new FireControl();    //Not Persistent
    }
    
    
    /**
     * Creates save directory if it doesn't already exist.
     */
    public void checkSaveDirectory()
    {
        new File("saves").mkdirs();
    }
    
    /**
     * Adds new miners to the player's LevelData
     * 
     * @param miner New miner to be stored with LevelData 
     */
    public void addMiner(Miner miner) {
        miners.add(miner);
    }
    public void addSmelter(Smelter smelter) {
        smelters.add(smelter);
    }
    public void addSamPlatform(SAM_PLATFORM samPlatform) {
        samPlatforms.add(samPlatform);
    }

    
    /**
     * Gets list of miners stored in the player's LevelData
     * 
     * @return Array list of current miners contained by <code>LevelData</code>
    */
    public ArrayList<Miner> getMiners() {
        return miners;
    }
    
    public ArrayList<Smelter> getSmelters() {
        return smelters;
    }
    public ArrayList<SAM_PLATFORM> getSamPlatforms() {
        return samPlatforms;
    }
    
    
    
    /**
     * Converts a standard Java Array to a JSON array usable by SimpleJSON library
     * 
     * 
     * @param inArray The input Java Array to be converted to a JSON Array
     * @return An equivalent JSON Array 
     */
    public JSONArray ToJsonArr(int inArray[])
    {
        JSONArray outArray = new JSONArray();
        for(int i=0; i < inArray.length; i++)
        {
            outArray.add(inArray[i]);
        }
                
        return outArray;
    }

    /**
     * Converts a 2D Java Array to a 2D JSON Array by creating 2 1D JSON Arrays and placing them in a final output array
     * 
     * @param inArray The input 2D array to be converted to a 2D JSON Array
     * @return An equivalent JSON Array composed of 2 other JSON Arrays
     */
    public JSONArray ToJson2DArr(int inArray[][])
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
    
    /**
     * Converts a JSON Array to a standard Java Array
     * 
     * @param inArray The input JSON Array to be converted to a Java Array
     * @return An equivalent Java Array
     */
    public int[] FromJsonArr(JSONArray inArray)
    {
        
        int outArray[] = new int[inArray.size()];
        for(int i = 0; i < inArray.size(); i++)
            {
                outArray[i] = ((Long)inArray.get(i)).intValue();                            
            }
        return outArray;
    }
    
    /**
     * Converts a JSON Array (of 2 Arrays) to a 2D Java Array
     * 
     * @param inArray The input JSON Array to be converted to a 2D Java Array
     * @return An equivalent 2D Java Array
     */
    public int[][] FromJson2DArr(JSONArray inArray)
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
    
    /**
     * Takes data from LevelData object and prints to a JSON file after making proper conversions
     * The file for the save is titled with the <code>name</code> of the LevelData object
     * 
     * @param player The input Player object containing all the LevelData data needing to be saved
     */
    public void logData(LevelData player)
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
    
    /**
     * Takes data from LevelData object and prints to a JSON file after making proper conversions
     * The file for the save is titled with the <code>name</code> of the LevelData object
     * 
     * @param name The input name used to locate the file for the corresponding LevelData
     * @return Data as a LevelData object just as it was originally entered with <code>logData</code>
     */
    public LevelData loadData(String name)
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
    /**
     * Converts map tiles into MapData by converting tile data to a single ID which represents the state of the tile (what's on it)
     * 
     * 
     * @param tiles array of tiles stored in the Grid (the whole map)
     * @return An Array of IDs representing the contents of each tile
    */
    public int[][] tiles2MapData(Tile tiles[][])
    {
        int mapSize = tiles[0].length;
        int[][] mapData = new int[mapSize][mapSize];
        int[] tileResources = new int[4];
        for(int i = 0; i < mapSize; i++)
        {
            for(int j = 0; j < mapSize; j++)
            {
                tileResources = tiles[i][j].getResources();
                if(tileResources[0] > 0)//Coal
                {
                    if(tiles[i][j].getMiner() != null)
                    {
                        mapData[i][j] = 11; //Coal ore w/ miner
                    }else
                    {
                        mapData[i][j] = 10; //Coal ore W/o Miner
                    }
                }else if(tileResources[1] > 0)//Copper
                {
                    if(tiles[i][j].getMiner() != null)
                    {
                        mapData[i][j] = 21; //Copper ore w/ miner
                    }else
                    {
                        mapData[i][j] = 20; //Copper ore W/o Miner
                    }
                }else if(tileResources[2] > 0)//Iron
                {
                    if(tiles[i][j].getMiner() != null)
                    {
                        mapData[i][j] = 31; //Iron ore w/ miner
                    }else
                    {
                        mapData[i][j] = 30; //Iron ore W/o Miner
                    }
                }else if(tileResources[3] > 0)//Gold
                {
                    if(tiles[i][j].getMiner() != null)
                    {
                        mapData[i][j] = 41; //Gold ore w/ miner
                    }else
                    {
                        mapData[i][j] = 40; //Gold ore W/o Miner
                    }
                }else
                {
                    if(tiles[i][j].getSmelter() != null)
                    {
                       mapData[i][j] = 1; //Grass W/ Smelter
                    }else
                    {
                       mapData[i][j] = 0; //Grass W/o smelter
                    }
                }
                
            }
        }
        return mapData;
    }
    
    /**
     * Converts MapData array IDs back into a tile array for regenerating the grid from a save
     * Data must be read from JSON save before this will run properly
     * 
     * @param mapData Array of grid IDs converted for saving
     * @param inv Player inventory, used for adding miners to the tiles
     * @return An array of tiles reconstructed from save data grid state IDs
     */
    public Tile[][] mapData2Tiles(int[][] mapData, Inventory inv)
    {
        int mapSize = mapData.length;
        Tile[][] tiles = new Tile[mapSize][mapSize];
        
        //NEED to use the inventory of main Aeroscape to create miners and smeleters from the data
        
        
        for (int i = 0; i < mapSize; i++)
        {
            for(int j = 0; j < mapSize; j++)
            {
                switch (mapData[i][j]) {
                //Grass Tile
                    case 0:
                        tiles[i][j] = new Tile(i, j, true);
                        break;
                //Grass Tile W/ Smelter
                    case 1:
                        tiles[i][j] = new Tile(i, j, false);
                        //add smelter
                        break;
                //Coal Ore W/o Miner
                    case 10:
                        tiles[i][j] = new Tile(i, j, true);
                        tiles[i][j].addResource(0,999999);
                        break;
                //Coal Ore W/ Miner
                    case 11:
                        tiles[i][j] = new Tile(i, j, false);
                        tiles[i][j].addResource(0, 999999);
                        //add miner
                        break;
                //Copper Ore W/o Miner
                    case 20:
                        tiles[i][j] = new Tile(i, j, true);
                        tiles[i][j].addResource(1, 999999);
                        break;
                //Copper Ore W Miner
                    case 21:
                        tiles[i][j] = new Tile(i, j, false);
                        tiles[i][j].addResource(1, 999999);
                        //add miner
                        break;
                //Iron Ore W/o Miner
                    case 30:
                        tiles[i][j] = new Tile(i, j, true);
                        tiles[i][j].addResource(2, 999999);
                        break;
                //Iron Ore W Miner
                    case 31:
                        tiles[i][j] = new Tile(i, j, false);
                        tiles[i][j].addResource(2, 999999);
                        //add miner
                        break;
                //Gold Ore W/o Miner
                    case 40:
                        tiles[i][j] = new Tile(i, j, true);
                        tiles[i][j].addResource(3, 999999);
                        break;
                //Gold Ore W Miner
                    case 41:
                        tiles[i][j] = new Tile(i, j, false);
                        tiles[i][j].addResource(3, 999999);
                        //add miner
                        break;
                    default:
                        break;
                }
            }
        }
        
        return null;
    }
    /*
    //Example Main function which can be used to save and load data to test the class
    //Last Tested 4/2/23
    public void main(String[] args)
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

