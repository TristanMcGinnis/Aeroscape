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

        generateResources();
    }

    private void generateResources() {
        Random rand = new Random();

        // Define probabilities for each resource type
        double[] probabilities = {0.02, 0.03, 0.01, 0.05}; // iron, copper, gold, coal

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                float brightness = rand.nextFloat() * 0.5f + 0.25f;  // Range: 0.25 to 0.75
                int grayValue = (int) (brightness * 255);
                Color randomColor = new Color(grayValue, grayValue, grayValue);
                tiles[i][j] = new Tile(i, j, true, randomColor);

                // randomly generate resources
                for (int resourceType = 0; resourceType < probabilities.length; resourceType++) {
                    if (rand.nextDouble() < probabilities[resourceType]) { // use probabilities
                        int resourceAmount = rand.nextInt(5) + 1; // generate 1-5 units of resources

                        // check neighboring tiles for same resource
                        if (i > 0 && tiles[i - 1][j].getResources()[resourceType] > 0) {
                            tiles[i][j].addResource(resourceType, resourceAmount);
                        } else if (j > 0 && tiles[i][j - 1].getResources()[resourceType] > 0) {
                            tiles[i][j].addResource(resourceType, resourceAmount);
                        } else {
                            tiles[i][j].addResource(resourceType, resourceAmount);
                        }
                    }
                }
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
}
