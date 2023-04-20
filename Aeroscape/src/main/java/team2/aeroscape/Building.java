package team2.aeroscape;

import java.awt.Graphics2D;

public abstract class Building extends Tile {

    protected int width;
    protected int height;
    public Building(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public Building(int width, int height, Tile tile) {
        super(tile);
        this.width = width;
        this.height = height;
    }

    public abstract void update();

}
