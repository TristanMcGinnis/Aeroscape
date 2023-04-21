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
    int lvl = 0; //unused
    int difficulty = 0; //unused
    int inventory[] = new int[10];
    int mapData[][];
    
    private ArrayList<Miner> miners;
    private ArrayList<Smelter> smelters;
    private ArrayList<SAM_PLATFORM> samPlatforms;
    FireControl FC;
    
    public void addMiner(Miner miner) {
        miners.add(miner);
    }

    public ArrayList<Miner> getMiners() {
        return miners;
    }
    
    public void addSmelter(Smelter smelter) {
        smelters.add(smelter);
    }

    public ArrayList<Smelter> getSmelters() {
        return smelters;
    }
    
    public void addSamPlatform(SAM_PLATFORM samPlatform) {
        samPlatforms.add(samPlatform);
    }

    public ArrayList<SAM_PLATFORM> getSamPlatforms() {
        return samPlatforms;
    }
    
    
    public Inventory getInventory()
    {
        Inventory returnInv = new Inventory();
        returnInv.addCoal(this.inventory[0]);
        returnInv.addCopper(this.inventory[1]);
        returnInv.addIron(this.inventory[2]);
        returnInv.addGold(this.inventory[3]);
        returnInv.addCopperIngot(this.inventory[4]);
        returnInv.addIronIngot(this.inventory[5]);
        returnInv.addGoldIngot(this.inventory[6]);
        return returnInv;
    }
    
    public int[][] getMapData()
    {
        return this.mapData;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Default constructor for LevelData
     * 
     */ 
    public LevelData()
    {
        this.miners = new ArrayList<Miner>();  
        this.smelters = new ArrayList<Smelter>(); 
        this.samPlatforms = new ArrayList<SAM_PLATFORM>();
    }
    
    
    /**
     * This constructor takes all relevant save information for logging/loading for persistence between sessions. 
     * This constructor is used outside of LevelData for managing saves.
     * 
     * @param name The name of the save
     * @param iInventory Inventory object containing all player inventory quantities
     * @param tiles main Tile array from the grid to be converted to a primitive data type for storage as mapData
     */ 
    public LevelData(String name, Inventory iInventory, Tile tiles[][])
    {
        this.name = name;
        this.lvl = 0;
        this.difficulty = 0;
        //Inventory tempInv = iInventory;
        this.inventory[0] = iInventory.getCoal();
        this.inventory[1] = iInventory.getCopper();
        this.inventory[2] = iInventory.getIron();
        this.inventory[3] = iInventory.getGold();
        this.inventory[4] = iInventory.getCopperIngot();
        this.inventory[5] = iInventory.getIronIngot();
        this.inventory[6] = iInventory.getGoldIngot();

        this.mapData = tiles2MapData(tiles);
    }
    
        /**
     * This constructor takes all relevant save information for logging/loading for persistence between sessions.
     * This constructor is used only within LevelData for loading data from a save file
     * 
     * @param name The name of the save
     * @param inventory Array of inventory values. Each index represents a set type of "resource"
     * @param mapData 2D Array storing the state of each grid of the save state. Values represent grid-state (ex: 0 is empty, 1 is MachineX, 2 is MachineY).
     */ 
    private LevelData(String name, int inventory[], int mapData[][])
    {
        this.name = name;
        this.mapData = mapData;
        this.inventory = inventory;
    }
    
    
    /**
     * Creates save directory if it doesn't already exist.
     */
    public void checkSaveDirectory()
    {
        new File("saves").mkdirs();
    }
    
    
    /**
     * Converts a standard Java Array to a JSON array usable by SimpleJSON library
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

                        //Read & Convert the mapData array
                        JSONArray jsonLevelData = (JSONArray) playerInfo.get("mapData");
                        int mapData[][];
                        mapData = FromJson2DArr(jsonLevelData);
                        
                        LevelData returnData = new LevelData(name, inventory, mapData);
                        return returnData;
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
     * @param tiles array of tiles stored in the Grid (the whole map)
     * @return An Array of IDs representing the contents of each tile
    */
    private int[][] tiles2MapData(Tile tiles[][])
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
                    }else if(tiles[i][j].getSamPlatform() != null)
                    {
                       mapData[i][j] = 2; //Grass W/ SAM Platform
                    }else{
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
    /*
       
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
    */
}

