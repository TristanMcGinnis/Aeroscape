package team2.aeroscape;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tile {
    private int x;
    private int y;
    private boolean walkable;
    private Color color;
    private ArrayList<GameObject> gameObjects;
    private int[] resources = new int[3]; // 0: iron, 1: copper, 2: gold


    public Tile(int x, int y, boolean walkable, Color color) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.color = color;
        this.gameObjects = new ArrayList<>();
        this.resources = new int[3]; // 0: iron
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

    
    public void draw(Graphics2D g2d, int tileSize) {
        if (resources[0] > 0) {
            // Show iron color
            g2d.setColor(Color.black);
        } else if (resources[1] > 0) {
            // Show copper color
            g2d.setColor(Color.orange);
        } else if (resources[2] > 0) {
            // Show gold color
            g2d.setColor(Color.yellow);
        } else {
            // Show default color
            g2d.setColor(color);
        }

        g2d.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
    }
}