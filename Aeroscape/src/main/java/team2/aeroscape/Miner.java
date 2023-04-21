package team2.aeroscape;

import java.awt.Color;
import java.awt.image.BufferedImage;


/**
This class represents a Miner building in the game. A Miner is a type of Building that mines resources from a Tile and adds them to an Inventory.
*/
public class Miner extends Building {
    private int miningSpeed = 1;
    private long lastMiningTime = 0; // Store the time of the last mining action
    private long miningInterval = 5000; // Set the idle time between mining actions in milliseconds
    private Inventory inventory;
    private Tile tile;

    private static BufferedImage minerTexture;
    
    /**
    Creates a new Miner with the specified Inventory and Tile.
    @param inventory the Inventory that the Miner adds mined resources to.
    @param tile the Tile that the Miner mines resources from.
    */
    public Miner(Inventory inventory, Tile tile) {
        super(50, 50, tile); // Set the width and height of the Miner building
        color = new Color(0,0,255);
        this.inventory = inventory;
        this.tile = tile;
        
        minerTexture = TextureEngine.minerTexture;
    }
    
    static {
        // This will trigger the static block in TextureEngine, loading the textures
        new TextureEngine();
    }
    
    
    /**
    Updates the Miner's state by checking if enough time has passed since the last mining action and performing a mining action if possible.
    */
    @Override
    public void update() {
        if (tile != null) {
            // Check if enough time has passed since the last mining action
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastMiningTime >= miningInterval) {
                // Check if the tile has any resources
                int[] resources = tile.getResources();
                boolean mined = false;
                for (int i = 0; i < resources.length; i++) {
                    if (resources[i] > 0) {
                        // Remove some amount of the resource from the tile and add it to the inventory
                        int amount = Math.min(resources[i], miningSpeed);
                        tile.addResource(i, -amount);
                        resources[i] -= amount; // Decrease the amount of the resource in the tile
                        switch (i) {
                            case 0:
                                inventory.addIron(amount);
                                break;
                            case 1:
                                inventory.addCopper(amount);
                                break;
                            case 2:
                                inventory.addGold(amount);
                                break;
                            case 3:
                                inventory.addCoal(amount);
                                break;
                        }
                        mined = true;
                        System.out.println("Resource mined");
                    }
                }
                if (mined) {
                    // If resources were mined, update the lastMiningTime
                    lastMiningTime = currentTime;
                    
                    int totalResources = 0;
                    
                    for (int resource : resources) {
                        totalResources += resource;
                    }
                    if (totalResources <= 0) {
                        System.out.println("Mined all resources");
                    }
                }
            }
        }
    }

    
    /**
    Returns the texture of the Miner.
    @return the texture of the Miner.
    */
    public BufferedImage getTexture() {
        return minerTexture;
    }   
}