package team2.aeroscape;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The `GridRenderer` class is a placeholder that extends `JPanel` and is responsible for rendering the game grid and circle. 
 * It has a static grid size and circle radius, as well as a `Camera` object that is used to apply a translation transform to the grid and circle based 
 * on the camera's position. The `circleX` and `circleY` static fields represent the position of the circle, and the `screenWidth` 
 * and `screenHeight` static fields represent the dimensions of the game screen.
*/
public class GridRenderer extends JPanel {
    
    // Initialize variables
    //private static final int GRID_SIZE = 50;
    private static final int CIRCLE_RADIUS = 20;
    private Player player;
    public final Camera camera;
    public static int circleX = 300;
    public static int circleY = 300;
    public static int screenWidth;
    public static int screenHeight;
    private Grid grid;
    private Miner miner;
    private LevelData levelData;
    
    
    public GridRenderer(Camera camera, Player player, LevelData levelData){
        
        this.player = player;
        this.camera = camera;
        this.levelData = levelData;
        setPreferredSize(new Dimension(1920, 1080));
        System.out.println("Grid Renderer initialized");
        screenWidth = getScreenWidth();
        screenHeight = getScreenHeight();
        circleX = screenWidth / 2;
        circleY = screenHeight / 2;
        int gridWidth = 100;  // Set desired grid width
        int gridHeight = 100; // Set desired grid height
        grid = new Grid(50, camera, gridWidth, gridHeight);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
        
    }
    
    
    public static int getCircleX() {
        return circleX;
    }
    
    
    public static int getCircleY() {
    return circleY;
    }
    
    
    public int getScreenWidth() {
        return getWidth();
    }
    
    
    public int getScreenHeight() {
        return getHeight();
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        
        //System.out.println("Painting");
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        camera.applyTransform(g2d); // Use the applyTransform method from the Camera class

        grid.drawGrid(g2d, getWidth(), getHeight());
        drawCircle(g2d);

        camera.resetTransform(g2d); // Use the resetTransform method from the Camera class
     
    for (Miner miner : levelData.getMiners()) {
        miner.render(g2d, camera);
    }
}

    
    
/*
 * The `drawCircle()` method draws the circle at the current position using the `Graphics2D` object passed to it. This method is also
 * called by the `paintComponent()` method.
*/
    private void drawCircle(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillOval(player.getX() - CIRCLE_RADIUS, player.getY() - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);
    }

    
    
    public void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        int step = (int) (5 + camera.getZoom());

        if (e.getID() == KeyEvent.KEY_PRESSED) {
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
        int gridSize = 50; // Assuming grid size is 50
        int gridX = worldX / gridSize * gridSize;
        int gridY = worldY / gridSize * gridSize;
        
        System.out.println("World X: " + worldX + ", World Y: " + worldY);
        
        Miner miner = new Miner(gridX, gridY);
        miner.setX(gridX);
        miner.setY(gridY);
        levelData.addMiner(miner);


        // Repaint the GridRenderer to display the miner
        repaint();
        
        System.out.println("Mouse clicked");
    }

}

