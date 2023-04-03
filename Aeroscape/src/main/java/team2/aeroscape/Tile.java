package team2.aeroscape;

import java.awt.*;
import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private boolean walkable;
    private Color color;
    private ArrayList<GameObject> gameObjects;

    public Tile(int x, int y, boolean walkable, Color color) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.color = color;
        this.gameObjects = new ArrayList<>();
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

    public void draw(Graphics2D g2d, int tileSize) {

        
        
        g2d.setColor(color);
        g2d.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
        
        //for (GameObject gameObject : gameObjects) {
            //gameObject.draw(g2d, x * tileSize, y * tileSize, tileSize);
        //}
    }
}