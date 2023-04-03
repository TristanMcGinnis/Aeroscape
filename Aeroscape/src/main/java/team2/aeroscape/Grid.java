package team2.aeroscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Grid {
    private int gridSize;
    private Camera camera;
    private Tile[][] tiles;

    public Grid(int gridSize, Camera camera, int gridWidth, int gridHeight) {
        this.gridSize = gridSize;
        this.camera = camera;
        this.tiles = new Tile[gridWidth][gridHeight];

        Random rand = new Random();
        
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                float brightness = rand.nextFloat() * 0.5f + 0.25f;  // Range: 0.25 to 0.75
                int grayValue = (int) (brightness * 255);
                Color randomColor = new Color(grayValue, grayValue, grayValue);
                tiles[i][j] = new Tile(i, j, true, randomColor);
            }
        }
    }

    public void drawGrid(Graphics2D g2d, int screenWidth, int screenHeight) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].draw(g2d, gridSize);
            }
        }
    }
    // Add methods for data handling, saving, and loading in the future
}
