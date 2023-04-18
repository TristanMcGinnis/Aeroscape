package team2.aeroscape;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Tile {
    protected int x;
    protected int y;
    private boolean walkable;
    protected Color color;
    protected BufferedImage texture;
    private ArrayList<GameObject> gameObjects;
    private int[] resources = new int[4]; // 0: iron, 1: copper, 2: gold, 3: coal
    private Miner miner;
    
    
    public Tile() {
        
    }
    
    //Initialize tile with no building
    //Tracks whether or not an individual tile has any resources or not; checked in Grid.generateResources()
    private boolean resourcesGenerated;
    
    
    BufferedImage ironTexture;
    BufferedImage copperTexture;
    BufferedImage goldTexture;
    BufferedImage coalTexture;
    BufferedImage grassTexture;
    
    public Tile(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;

        File file = new File("/team2/aeroscape/textures/iron.png");

        if (file.exists()) {
            System.out.println("File found at: " + file.getAbsolutePath());
        } else {
            System.out.println("File not found!");
        }
        
        
        try {
            ironTexture = ImageIO.read(getClass().getResource("/team2/aeroscape/textures/iron.png"));
            copperTexture = ImageIO.read(getClass().getResource("/team2/aeroscape/textures/copper.png"));
            goldTexture = ImageIO.read(getClass().getResource("/team2/aeroscape/textures/gold.png"));
            coalTexture = ImageIO.read(getClass().getResource("/team2/aeroscape/textures/coal.png"));
            grassTexture = ImageIO.read(getClass().getResource("/team2/aeroscape/textures/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        this.gameObjects = new ArrayList<>();
        this.resources = new int[4]; // 0: iron, 1: copper, 2: gold, 3: coal
        this.miner = miner;
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

        if (miner != null) {
            currentTexture = miner.getTexture();
        } else if (resources[0] > 0) {
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
        }
        else {
            currentTexture = grassTexture;
        }

        g2d.drawImage(currentTexture, x * tileSize, y * tileSize, tileSize, tileSize, null);
    }

    
    public Miner getMiner() {
        return miner;
    }

    public void setMiner(Miner miner) {
        this.miner = miner;
    }
}
