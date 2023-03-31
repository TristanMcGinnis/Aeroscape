/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 *
 * @author Jacob
 */
public class GridRenderer extends JPanel {
    private static final int GRID_SIZE = 50;
    private static final int CIRCLE_RADIUS = 20;
    public final Camera camera = new Camera(0, 0);
    public static int circleX = 300;
    public static int circleY = 300;
    public static int screenWidth;
    public static int screenHeight;
    //private final Camera camera = new Camera(0, 0);

    public GridRenderer(){
        System.out.println("Grid Renderer initialized");
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        //GridRenderer gridRenderer = new GridRenderer();
        frame.add(this);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
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
        return getWidth() * 2;
    }
    
    public int getScreenHeight() {
        return getHeight() * 2;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        System.out.println("Painting");
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        camera.applyTransform(g2d);
        drawGrid(g2d);
        drawCircle(g2d);
        camera.resetTransform(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
        int width = getWidth() * 2;
        int height = getHeight() * 2;
        System.out.println("Drawing Grid");
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));

        // Draw horizontal lines
        for (int i = 0; i <= height; i += GRID_SIZE) {
            g2d.drawLine(0, i, width, i);
        }

        // Draw vertical lines
        for (int i = 0; i <= width; i += GRID_SIZE) {
            g2d.drawLine(i, 0, i, height);
        }
    }

    private void drawCircle(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillOval(circleX - CIRCLE_RADIUS, circleY - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);
    }

    public void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        int step = 5;

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

        camera.follow(circleX, circleY, getWidth(), getHeight());
        repaint();
    }
}

