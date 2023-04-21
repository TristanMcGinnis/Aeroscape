package team2.aeroscape;
import java.awt.Graphics2D;
import java.util.Random;

/**
The Grid class represents the game map and manages the Tiles within it.
It generates resources on the Tiles and draws the relevant Tiles based on the camera's viewport.
*/
public class Grid {

    private int gridSize;
    private Camera camera;
    private Tile[][] tiles;
    

    /**
     * Constructor for the Grid class.
     * Initializes the size of the map, camera, and Tiles, and generates resources on the Tiles.
     *
     * @param gridSize - The size of the grid.
     * @param camera - The camera object used to view the grid.
     * @param gridWidth - The width of the grid.
     * @param gridHeight - The height of the grid.
     */
    public Grid(int gridSize, Camera camera, int gridWidth, int gridHeight) {
        this.gridSize = gridSize;
        this.camera = camera;
        this.tiles = new Tile[gridWidth][gridHeight];
        
        generateResources();
    }
    
    /**
     * Generates resources on the Tiles based on randomly generated probabilities.
     * This method is called during the initialization of the Grid object.
     */
    private void generateResources() {
        Random rand = new Random();

        // Define probabilities for each resource type
        double[] probabilities = {0.02, 0.03, 0.01, 0.05}; // iron, copper, gold, coal

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(i, j, true);

                // randomly generate resources
                for (int resourceType = 0; resourceType < probabilities.length; resourceType++) {
                    
                    //Currently checks using a bool to see if the Tile already has a resource on it. This will have an impact on the odds of each resource appearing, giving preference to resources earlier in the probabilities array
                    if (rand.nextDouble() < probabilities[resourceType] && !tiles[i][j].getResourcesGenerated()) { // use probabilities //and check if the Tile already has a resource generated onto it
                        int resourceAmount = rand.nextInt(451) + 50; // generate 50-500 units of resources
                        
                        tiles[i][j].setResourcesGenerated(true);
                        
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
    * Draws the relevant Tiles based on the camera's viewport.
    *
    * @param g2d - The graphics context to draw on.
    * @param screenWidth - The width of the screen.
    * @param screenHeight - The height of the screen.
    */
    public void drawGrid(Graphics2D g2d, int screenWidth, int screenHeight) {
        // Get the range of tile indices in the frame of the camera
        int[] topLeftIndex = camera.getTileIndex(0, 0, gridSize);
        int[] bottomRightIndex = camera.getTileIndex(screenWidth, screenHeight, gridSize);
        int minI = Math.max(topLeftIndex[0], 0);
        int maxI = Math.min(bottomRightIndex[0], tiles.length - 1);
        int minJ = Math.max(topLeftIndex[1], 0);
        int maxJ = Math.min(bottomRightIndex[1], tiles[0].length - 1);

        // Iterate through the relevant tiles only
        for (int i = minI; i <= maxI; i++) {
            for (int j = minJ; j <= maxJ; j++) {
                tiles[i][j].draw(g2d, gridSize);
            }
        }
    }

     /**
     * Returns the Tile object at the given position.
     *
     * @param x - The x position of the Tile.
     * @param y - The y position of the Tile.
     * @return The Tile object at the given position, or null if out of bounds.
     */
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= tiles.length || y >= tiles[x].length) {
            return null; // return null if the tile is out of bounds
        }
        return tiles[x][y];
    }
    
    /**
    * Sets the Tile object at the given position.
    *
    * @param x - The x position of the Tile.
    * @param y - The y position of the Tile.
    * @param tile - The Tile object to set.
    */
    public void setTile(int x, int y, Tile tile) {
        if (x < 0 || y < 0 || x >= tiles.length || y >= tiles[x].length) {
            return;
        }
        
        tiles[x][y] = tile;
    }

    /**
    * Returns the size of the grid.
    *
    * @return The size of the grid.
    */
    public int getGridSize() {
        return gridSize;
    }
}
