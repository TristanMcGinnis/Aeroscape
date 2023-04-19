package team2.aeroscape;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tile {
    protected int x;
    protected int y;
    private boolean walkable;
    protected Color color;
    private ArrayList<GameObject> gameObjects;
    private int[] resources = new int[4]; // 0: iron, 1: copper, 2: gold, 3: coal
    private Miner miner;
    private Smelter smelter;
    
    public Tile() {
}
    
    //Initialize tile with no building
    //Tracks whether or not an individual tile has any resources or not; checked in Grid.generateResources()
    private boolean resourcesGenerated;
    
    
    private static BufferedImage ironTexture;
    private static BufferedImage copperTexture;
    private static BufferedImage goldTexture;
    private static BufferedImage coalTexture;
    private static BufferedImage grassTexture;
    private BufferedImage texture;
    
    static {
        try {
            Path ironPath = Paths.get("src/main/resources/textures/iron.png");
            Path copperPath = Paths.get("src/main/resources/textures/copper.png");
            Path goldPath = Paths.get("src/main/resources/textures/gold.png");
            Path coalPath = Paths.get("src/main/resources/textures/coal.png");
            Path grassPath = Paths.get("src/main/resources/textures/grass.png");

            ironTexture = ImageIO.read(Files.newInputStream(ironPath));
            copperTexture = ImageIO.read(Files.newInputStream(copperPath));
            goldTexture = ImageIO.read(Files.newInputStream(goldPath));
            coalTexture = ImageIO.read(Files.newInputStream(coalPath));
            grassTexture = ImageIO.read(Files.newInputStream(grassPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public Tile(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
     
        
        this.gameObjects = new ArrayList<>();
        this.resources = new int[4]; // 0: iron, 1: copper, 2: gold, 3: coal
        this.miner = miner;
        this.smelter = smelter;
        // Defaults hasResources to false on creation
        this.resourcesGenerated = false;
        this.texture = grassTexture;
        
    }

    
    //Copy Constructor
    public Tile(Tile tile) {
        x = tile.x;
        y = tile.y;
        walkable = tile.walkable;
        color = tile.color;
        gameObjects = tile.gameObjects;
        resources = tile.resources;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }
    
    public void addResource(int resourceType, int resourceAmount) {
        resources[resourceType] += resourceAmount;
    }
    
    public int[] getResources() {
        return resources;
    }
    
    public void setResourcesGenerated(boolean tf){
        resourcesGenerated = tf;
    }
    
    public boolean getResourcesGenerated(){
        return resourcesGenerated;
    }

    
    public void draw(Graphics2D g2d, int tileSize) {
        BufferedImage currentTexture = texture;

        if (resources[0] > 0) {
            // Set iron texture
            currentTexture = ironTexture;
        } else if (resources[1] > 0) {
            // Set copper texture
            currentTexture = copperTexture;
        } else if (resources[2] > 0) {
            // Set gold texture
            currentTexture = goldTexture;
        } else if (resources[3] > 0) {
            // Set coal texture
            currentTexture = coalTexture;
        } else {
            currentTexture = grassTexture;
        }

        g2d.drawImage(currentTexture, x * tileSize, y * tileSize, tileSize, tileSize, null);

        // Draw the miner texture on top of the current texture if a miner is present
        if (miner != null) {
            g2d.drawImage(miner.getTexture(), x * tileSize, y * tileSize, tileSize, tileSize, null);
        }
    }

    
    public Miner getMiner() {
        return miner;
    }

    public void setMiner(Miner miner) {
        this.miner = miner;
    }
    
    public boolean hasResources() {
        // Check if the tile has any resources
        for (int resourceAmount : resources) {
            if (resourceAmount > 0) {
                return true;
            }
        }
        return false;
    }

    public Smelter getSmelter() {
        return smelter;
    }

    public void setSmelter(Smelter smelter) {
        this.smelter = smelter;
    }
}
