package team2.aeroscape;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
    public final Camera camera;
    public static int circleX = 300;
    public static int circleY = 300;
    public static int screenWidth;
    public static int screenHeight;
    private Grid grid;
    
    
    public GridRenderer(Camera camera){
       
        this.camera = camera;
        setPreferredSize(new Dimension(1920, 1080));
        System.out.println("Grid Renderer initialized");
        screenWidth = getScreenWidth();
        screenHeight = getScreenHeight();
        circleX = screenWidth / 2;
        circleY = screenHeight / 2;
        int gridWidth = 100;  // Set desired grid width
        int gridHeight = 100; // Set desired grid height
        grid = new Grid(50, camera, gridWidth, gridHeight);
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
        System.out.println("Painting");
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.scale(camera.getZoom(), camera.getZoom());
        g2d.translate(-camera.getX(), -camera.getY());
        
        grid.drawGrid(g2d, getWidth(), getHeight());
        
        drawCircle(g2d);

    }

    
    
/*
 * The `drawCircle()` method draws the circle at the current position using the `Graphics2D` object passed to it. This method is also
 * called by the `paintComponent()` method.
*/
    private void drawCircle(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillOval(circleX - CIRCLE_RADIUS, circleY - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);
    }

    
    
    public void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        int step = (int) (5 + camera.getZoom());

        switch (key) {
            case KeyEvent.VK_W:
                circleY -= step;
                break;
            case KeyEvent.VK_A:
                circleX -= step;
                break;
            case KeyEvent.VK_S:
                circleY += step;
                break;
            case KeyEvent.VK_D:
                circleX += step;
                break;
        }
        repaint();
    }
}

