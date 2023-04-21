package team2.aeroscape;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
A Tile represents a single square on the game map. It has an (x, y) coordinate
position, a boolean to indicate whether or not it is walkable, a Color object for
its visual representation, and an array of resources (iron, copper, gold, coal) that
can be mined from it.
*/
public class Tile {
    protected int x;
    protected int y;
    private boolean walkable;
    private boolean resourcesGenerated;
    protected Color color;
    private int[] resources = new int[4]; // 0: iron, 1: copper, 2: gold, 3: coal
    private Miner miner;
    private Smelter smelter;
    private SAM_PLATFORM samPlatform;
    private BufferedImage texture;
    private TextureEngine textureEngine;
    
    static {
        new TextureEngine();
    }
    
    /**
    Initializes a new Tile object with default parameters.
    */
    public Tile() { }

    /**
    Initializes a new Tile object with given (x, y) coordinates and walkable status.
    @param x the x-coordinate of the tile
    @param y the y-coordinate of the tile
    @param walkable whether or not the tile is walkable
    */
    public Tile(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.resources = new int[4]; // 0: iron, 1: copper, 2: gold, 3: coal
        this.miner = miner;
        this.smelter = smelter;
        this.samPlatform = samPlatform;
        // Defaults hasResources to false on creation
        this.resourcesGenerated = false;
        this.textureEngine = textureEngine;
        
    }

    
    /**
    Initializes a new Tile object as a copy of an existing Tile object.
    @param tile the Tile object to be copied
    */
    public Tile(Tile tile) {
        x = tile.x;
        y = tile.y;
        walkable = tile.walkable;
        color = tile.color;
        resources = tile.resources;
    }

    /**
    Draws the appropriate texture for the tile, based on the resources it has and whether a miner, smelter or
    SAM platform is present.
    @param g2d The graphics context to draw on.
    @param tileSize The size of each tile.
    */
    public void draw(Graphics2D g2d, int tileSize) {
        
        BufferedImage currentTexture = texture;
        if (resources[0] > 0) {
            currentTexture = TextureEngine.ironTexture;
        } else if (resources[1] > 0) {
            currentTexture = TextureEngine.copperTexture;
        } else if (resources[2] > 0) {
            currentTexture = TextureEngine.goldTexture;
        } else if (resources[3] > 0) {
            currentTexture = TextureEngine.coalTexture;
        } else
            currentTexture = TextureEngine.grassTexture;
        
        g2d.drawImage(currentTexture, x * tileSize, y * tileSize, tileSize, tileSize, null);

        // Draw the miner texture on top of the current texture if a miner is present
        if (miner != null) {
            g2d.drawImage(TextureEngine.minerTexture, x * tileSize, y * tileSize, tileSize, tileSize, null);
        }
        if (smelter != null) {
            g2d.drawImage(TextureEngine.smelterTexture, x * tileSize, y * tileSize, tileSize, tileSize, null);
        }
        if (samPlatform != null) {
            g2d.drawImage(TextureEngine.sam_Tier_1_Texture, x * tileSize, y * tileSize, tileSize, tileSize, null);
        }
    }

    /**
    Returns the x-coordinate of the tile.
    @return the x-coordinate of the tile
    */
    public int getX() {
    return x;
    }
    /**
    Returns the y-coordinate of the tile.
    @return the y-coordinate of the tile
    */
    public int getY() {
    return y;
    }
    /**
    Returns whether or not the tile is walkable.
    @return true if the tile is walkable, false otherwise
    */
    public boolean isWalkable() {
    return walkable;
    }
    /**
    Returns the color of the tile.
    @return the color of the tile
    */
    public Color getColor() {
    return color;
    }
    /**
    Adds a certain amount of a specified resource to the tile's resource array.
    @param resourceType the type of resource to be added
    @param resourceAmount the amount of resource to be added
    */
    public void addResource(int resourceType, int resourceAmount) {
    resources[resourceType] += resourceAmount;
    }
    /**
    Returns an array containing the amounts of each resource currently available
    in the tile.
    @return an int[] representing the amounts of iron, copper, gold, and coal resources in the tile
    */
    public int[] getResources() {
    return resources;
    }
    
    /**
    Returns the miner currently on top of the tile (if any).
    @return The miner currently on top of the tile (if any).
    */
    public Miner getMiner() {
        return miner;
    }

    /**
    Sets the miner currently on top of the tile.
    @param miner The miner to set on top of the tile.
    */
    public void setMiner(Miner miner) {
        this.miner = miner;
    }
    
    /**
    Returns true if the tile has any resources.
    @return if the tile has any resources.
    */
    public boolean hasResources() {
        // Check if the tile has any resources
        for (int resourceAmount : resources) {
            if (resourceAmount > 0) {
                return true;
            }
        }
        return false;
    }

    /**
    Returns the smelter.
    @return the smelter.
    */
    public Smelter getSmelter() {
        return smelter;
    }

    /**
    Sets the smelter currently on top of the tile.
    @param smelter The smelter to set on top of the tile.
    */
    public void setSmelter(Smelter smelter) {
        this.smelter = smelter;
    }  
    
    /**
    Returns the SAM_PLATFORM.
    @return the SAM_PLATFORM.
    */
    public SAM_PLATFORM getSamPlatform() {
        return samPlatform;
    }

    /**
    Sets the SAM Platform currently on top of the tile.
    @param samPlatform The SAM Platform to set on top of the tile.
    */
    public void setSamPlatform(SAM_PLATFORM samPlatform) {
        this.samPlatform = samPlatform;
    }
    
    /**
    Returns true/false based on if the tile has already had resources generated on it.
    @return resourcesGenerated
    */
    public boolean getResourcesGenerated() {
        return resourcesGenerated;
    }
    
    /** 
     * Sets the resources generated boolean.
     @param value The true/false passed in to set resourcesGenerated.
     */
    public void setResourcesGenerated(boolean value) {
        resourcesGenerated = value;
    }
}
