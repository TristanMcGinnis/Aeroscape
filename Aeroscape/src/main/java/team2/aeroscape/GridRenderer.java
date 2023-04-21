package team2.aeroscape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


/**
 * The `GridRenderer` class is responsible for rendering the game grid and the player's texture. It extends `JPanel` and uses a `Camera` object
 * to apply a translation transform to the grid and circle based on the camera's position.
 */
public class GridRenderer extends JPanel {
    
    // Initialize constants
    private static final int PLAYER_SIZE = 40;
    private static final int TILE_SIZE = 50;

    
    private final Camera camera;
    private final Player player;
    private final LevelData levelData;
    private final Grid grid;
    private final Inventory inventory;
    
    private int screenWidth;
    private int screenHeight;
    private TextureEngine textureEngine;
    private int GRID_WIDTH;
    private int GRID_HEIGHT;
    
    
    static {
        new TextureEngine();
    }
    
    
    /**
    The Placer interface provides a method to place an object on a tile in the game grid based on the given Tile and Inventory.
    */
    public interface Placer {
        void place(Tile tile, Inventory inventory);
    }
    
    
    /**
     * Constructs a new `GridRenderer` object with the specified `Camera`, `Player`, and `LevelData`.
     *
     * @param camera The camera object used to apply a translation transform.
     * @param player The player object to render on the grid.
     * @param levelData The level data for the current level.
     * @param inventory The inventory of the player.
     * @param gridWidth The width of the game grid.
     * @param gridHeight The height of the game grid.
     */
    public GridRenderer(Camera camera, Player player, LevelData levelData, Inventory inventory, int gridWidth, int gridHeight) {
        this.camera = camera;
        this.player = player;
        this.levelData = levelData;
        this.inventory = inventory;
        this.GRID_WIDTH = gridWidth;
        this.GRID_HEIGHT = gridHeight;
        this.grid = new Grid(TILE_SIZE, camera, GRID_WIDTH, GRID_HEIGHT);
        this.screenWidth = 1920;
        this.screenHeight = 1080;
        this.textureEngine = textureEngine;
        setPreferredSize(new Dimension(screenWidth, screenHeight));
    }
    
    
    /**
     * Returns the current X coordinate of the player's circle.
     *
     * @return The current X coordinate of the player's circle.
     */
    public int getCircleX() {
        return player.getX();
    }
    
    
    /**
     * Returns the current Y coordinate of the player's circle.
     *
     * @return The current Y coordinate of the player's circle.
     */
    public int getCircleY() {
        return player.getY();
    }
    
    
    /**
     * Returns the width of the game screen.
     *
     * @return The width of the game screen.
     */
    public int getScreenWidth() {
        return screenWidth;
    }
    
    
    /**
     * Returns the height of the game screen.
     *
     * @return The height of the game screen.
     */
    public int getScreenHeight() {
        return screenHeight;
    }
    
    
    /**
     * Paints the grid and the player's circle on the panel using the specified `Graphics` object.
     *
     * @param g The graphics object used to paint the components on the panel.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Retrieve the Image object from the ImageIcon
        Image waterImage = textureEngine.waterTexture.getImage();

        // Create a BufferedImage from the water image
        BufferedImage waterBufferedImage = new BufferedImage(waterImage.getWidth(null), waterImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dWater = waterBufferedImage.createGraphics();
        g2dWater.drawImage(waterImage, 0, 0, null);
        g2dWater.dispose();

        // Calculate the location of the rectangle that defines the region where the texture will be painted
        int waterX = -(camera.getX() % waterImage.getWidth(null));
        int waterY = -(camera.getY() % waterImage.getHeight(null));
        Rectangle waterRect = new Rectangle(waterX, waterY, 150, 150);

        // Create a TexturePaint object using the water image and the rectangle
        TexturePaint waterPaint = new TexturePaint(waterBufferedImage, waterRect);

        // Use the TexturePaint object to fill the panel
        g2d.setPaint(waterPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        camera.applyTransform(g2d); // Use the applyTransform method from the Camera class

        grid.drawGrid(g2d, screenWidth, screenHeight);
        drawPlayer(g2d);

        camera.resetTransform(g2d); // Use the resetTransform method from the Camera class
     
        //renderMiners(g2d);
        drawResources(g2d);
        g2d.dispose();
    }
    
    /**
     * Draws the player texture on the panel using the specified `Graphics2D` object.
     *
     * @param g2d The graphics object used to draw the player's circle.
     */
    private void drawPlayer(Graphics2D g2d) {
        int x = player.getX() - PLAYER_SIZE;
        int y = player.getY() - PLAYER_SIZE;
        g2d.drawImage(textureEngine.playerTexture, x, y, 2 * PLAYER_SIZE, 2 * PLAYER_SIZE, null);
    }
    

    /**
     * Draws the resources on the panel using the specified `Graphics2D` object.
     *
     * @param g2d The graphics object used to draw the resources.
     */
    private void drawResources(Graphics2D g2d) {
        // Set color and font
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));

        // Get resources from the inventory
        int iron = inventory.getIron();
        int copper = inventory.getCopper();
        int gold = inventory.getGold();
        int coal = inventory.getCoal();
        int ironIngot = inventory.getIronIngot();
        int copperIngot = inventory.getCopperIngot();
        int goldIngot = inventory.getGoldIngot();

        // Draw the resource text on the top left of the screen
        g2d.drawString("Iron: " + iron, 10, 30);
        g2d.drawString("Copper: " + copper, 10, 60);
        g2d.drawString("Gold: " + gold, 10, 90);
        g2d.drawString("Coal: " + coal, 10, 120);
        g2d.drawString("Iron Ingot: " + ironIngot, 10, 150);
        g2d.drawString("Copper Ingot: " + copperIngot, 10, 180);
        g2d.drawString("Gold Ingot: " + goldIngot, 10, 210);
    }
    
    
    /**
    Handles the placement of an object on a tile based on the given MouseEvent and Placer.
    @param e The mouse event that triggered the placement.
    @param placer The Placer that will place the object on the tile.
    */
    private void handlePlacement(MouseEvent e, Placer placer) {
        int x = e.getX();
        int y = e.getY();

        // Convert screen coordinates to world coordinates
        int worldX = (int) ((x + camera.getX()) / camera.getZoom());
        int worldY = (int) ((y + camera.getY()) / camera.getZoom() - 25);

        // Snap to the grid
        int gridX = (worldX / TILE_SIZE) * TILE_SIZE;
        int gridY = (worldY / TILE_SIZE) * TILE_SIZE;

        System.out.println("World X: " + worldX + ", World Y: " + worldY);

        Tile tile = grid.getTile(gridX / TILE_SIZE, gridY / TILE_SIZE);

        if (tile != null) {
            placer.place(tile, inventory);
            repaint();
        } else {
            System.out.println("Tile not found at (" + gridX + ", " + gridY + ")");
        }
    }
    

    /**
    * This method handles the placement of a miner object on the game board based on a given MouseEvent.
    *
    * @param e The MouseEvent that occurred.
    */
    public void handleMinerPlacement(MouseEvent e) {
        handlePlacement(e, (tile, inventory) -> {
            // Place a miner on the tile, if there's no miner yet
            if (tile.hasResources() && tile.getMiner() == null) {
                Miner miner = new Miner(inventory, tile);
                tile.setMiner(miner);
                levelData.addMiner(miner);
            }
        });
    }

    /**
    * This method handles the placement of a smelter object on the game board based on a given MouseEvent.
    *
    * @param e The MouseEvent that occurred.
    */
    public void handleSmelterPlacement(MouseEvent e) {
        handlePlacement(e, (tile, inventory) -> {
            // Place a smelter on the tile, if there's no smelter yet
            if (tile.getSmelter() == null && !tile.hasResources()) {
                Smelter smelter = new Smelter(inventory, tile);
                tile.setSmelter(smelter);
                levelData.addSmelter(smelter);
            }
        });
    }

    /**
    * This method handles the placement of a SAM platform object on the game board based on a given MouseEvent.
    *
    * @param e The MouseEvent that occurred.
    */
    public void handleSamPltPlacement(MouseEvent e) {
        handlePlacement(e, (tile, inventory) -> {
            // Place a SAM platform on the tile, if there's no SAM platform yet
            if (tile.getSamPlatform() == null && !tile.hasResources()) {
                SAM_PLATFORM samPlatform = new SAM_PLATFORM(tile);
                tile.setSamPlatform(samPlatform);
                levelData.addSamPlatform(samPlatform);
            }
        });
    }
}

