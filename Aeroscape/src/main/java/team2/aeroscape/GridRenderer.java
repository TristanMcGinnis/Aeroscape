package team2.aeroscape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * The `GridRenderer` class is responsible for rendering the game grid and the player's circle. It extends `JPanel` and uses a `Camera` object
 * to apply a translation transform to the grid and circle based on the camera's position.
 */
public class GridRenderer extends JPanel {
    
    // Initialize constants
    private static final int CIRCLE_RADIUS = 20;
    private static final int GRID_SIZE = 50;
    
    // Declare instance variables
    private final Camera camera;
    private final Player player;
    private final LevelData levelData;
    private final Grid grid;
    private int screenWidth;
    private int screenHeight;
    
    /**
     * Constructs a new `GridRenderer` object with the specified `Camera`, `Player`, and `LevelData`.
     */
    public GridRenderer(Camera camera, Player player, LevelData levelData) {
        this.camera = camera;
        this.player = player;
        this.levelData = levelData;
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
        drawCircle(g2d);

        camera.resetTransform(g2d); // Use the resetTransform method from the Camera class
     
        for (Miner miner : levelData.getMiners()) {
            miner.render(g2d, camera);
        }
    }
    
    /**
     * Draws the player's circle on the panel using the specified `Graphics2D` object.
     */
    private void drawCircle(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillOval(player.getX() - CIRCLE_RADIUS, player.getY() - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);
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

        Miner miner = new Miner(gridX, gridY);
        levelData.addMiner(miner);

        repaint();
    }
}

