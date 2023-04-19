package team2.aeroscape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The `GridRenderer` class is responsible for rendering the game grid and the player's circle. It extends `JPanel` and uses a `Camera` object
 * to apply a translation transform to the grid and circle based on the camera's position.
 */
public class GridRenderer extends JPanel {
    
    // Initialize constants
    private static final int CIRCLE_RADIUS = 40;
    private static final int GRID_SIZE = 50;
    private static BufferedImage playerTexture;
    // Declare instance variables
    private final Camera camera;
    private final Player player;
    private final LevelData levelData;
    private final Grid grid;
    private final Inventory inventory;
    private int screenWidth;
    private int screenHeight;
    
    static {
        try {
            Path playerPath = Paths.get("src/main/resources/textures/player.png");
            playerTexture = ImageIO.read(Files.newInputStream(playerPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Constructs a new `GridRenderer` object with the specified `Camera`, `Player`, and `LevelData`.
     */
    public GridRenderer(Camera camera, Player player, LevelData levelData, Inventory inventory) {
        this.camera = camera;
        this.player = player;
        this.levelData = levelData;
        this.inventory = inventory;
        this.grid = new Grid(GRID_SIZE, camera, 100, 100);
        this.screenWidth = 1920;
        this.screenHeight = 1080;
        
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }
    
    /**
     * Returns the current X coordinate of the player's circle.
     */
    public int getCircleX() {
        return player.getX();
    }
    
    /**
     * Returns the current Y coordinate of the player's circle.
     */
    public int getCircleY() {
        return player.getY();
    }
    
    /**
     * Returns the width of the game screen.
     */
    public int getScreenWidth() {
        return screenWidth;
    }
    
    /**
     * Returns the height of the game screen.
     */
    public int getScreenHeight() {
        return screenHeight;
    }
    
    /**
     * Paints the grid and the player's circle on the panel using the specified `Graphics` object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        camera.applyTransform(g2d); // Use the applyTransform method from the Camera class

        grid.drawGrid(g2d, screenWidth, screenHeight);
        drawPlayer(g2d);

        camera.resetTransform(g2d); // Use the resetTransform method from the Camera class
     
        renderMiners(g2d);
        drawResources(g2d);
        g2d.dispose();
    }
    
    /**
     * Draws the player's circle on the panel using the specified `Graphics2D` object.
     */
    private void drawPlayer(Graphics2D g2d) {
        int x = player.getX() - CIRCLE_RADIUS;
        int y = player.getY() - CIRCLE_RADIUS;
        g2d.drawImage(playerTexture, x, y, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, null);
    }
    
    private void drawResources(Graphics2D g2d) {
        // Set color and font
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));

        // Get resources from the inventory
        int iron = inventory.getIron();
        int copper = inventory.getCopper();
        int gold = inventory.getGold();
        int coal = inventory.getCoal();

        // Draw the resource text on the top left of the screen
        g2d.drawString("Iron: " + iron, 10, 30);
        g2d.drawString("Copper: " + copper, 10, 60);
        g2d.drawString("Gold: " + gold, 10, 90);
        g2d.drawString("Coal: " + coal, 10, 120);
    }

    
    
    /**
     * Handles key presses by updating the player's velocity and repainting the panel.
     */
    public void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        int step = (int) (5 + camera.getZoom());

        if (e.getID() == KeyEvent.KEY_PRESSED)
        {
            switch (key) {
                case KeyEvent.VK_W:
                    player.setVelY(-step);
                    break;
                case KeyEvent.VK_A:
                    player.setVelX(-step);
                    break;
                case KeyEvent.VK_S:
                    player.setVelY(step);
                    break;
                case KeyEvent.VK_D:
                    player.setVelX(step);
                    break;
            }
        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            switch (key) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_S:
                    player.stopY();
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_D:
                    player.stopX();
                    break;
            }
        }
        repaint();
    }
    
    public void handleMouseClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Convert screen coordinates to world coordinates
        int worldX = (int) ((x + camera.getX()) / camera.getZoom());
        int worldY = (int) ((y + camera.getY()) / camera.getZoom());

        // Snap to the grid
        int gridX = (worldX / GRID_SIZE) * GRID_SIZE;
        int gridY = (worldY / GRID_SIZE) * GRID_SIZE;

        System.out.println("World X: " + worldX + ", World Y: " + worldY);

        Tile tile = grid.getTile(gridX / GRID_SIZE, gridY / GRID_SIZE);

        // Place a miner on the tile, if there's no miner yet
        if (tile != null && tile.hasResources() && tile.getMiner() == null) {
            Miner miner = new Miner(inventory, tile);
            tile.setMiner(miner);
            levelData.addMiner(miner);

            repaint();
        } else {
            System.out.println("Tile not found at (" + gridX + ", " + gridY + ")");
        }
    }

    public void renderMiners(Graphics2D g2d) {
        for (Miner miner : levelData.getMiners()) {
            miner.render(g2d, camera);
        }
    }
}

