/**

The Grid class represents a grid of tiles that make up a game world. It generates resources on the
tiles using given probabilities and draws the tiles on the screen.
*/
package team2.aeroscape;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *
 * @author Jacob
 */
public class Grid {
/**
 * The size of the grid.
 */
    private int gridSize;

/**
 * The camera used to view the grid.
 */
    private Camera camera;

/**
 * The array of tiles that make up the grid.
 */
    private Tile[][] tiles;

/**
 * Constructs a Grid object with the given parameters.
 *
 * @param gridSize The size of the grid.
 * @param camera The camera used to view the grid.
 * @param gridWidth The width of the grid.
 * @param gridHeight The height of the grid.
 */
    public Grid(int gridSize, Camera camera, int gridWidth, int gridHeight) {
        this.gridSize = gridSize;
        this.camera = camera;
        this.tiles = new Tile[gridWidth][gridHeight];

        generateResources();
    }

/**
 * Generates resources on each tile using given probabilities.
 * Each tile is assigned a random color.
 */
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
     
/**
 * Draws the grid on the screen using the given Graphics2D object and screen dimensions.
 *
 * @param g2d The Graphics2D object used to draw the grid.
 * @param screenWidth The width of the screen.
 * @param screenHeight The height of the screen.
 */
    public void drawGrid(Graphics2D g2d, int screenWidth, int screenHeight) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].draw(g2d, gridSize);

            }
        }
    }
}
