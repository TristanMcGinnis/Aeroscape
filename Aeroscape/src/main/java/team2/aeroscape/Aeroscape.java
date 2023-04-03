// Import necessary libraries
package team2.aeroscape;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * CS 321-01 TEAM 2
 * AEROSCAPE
 */

// This class represents the main game engine.
public class Aeroscape {
    
    // Initialize variables
    public final Camera camera = new Camera(0, 0, 1.0);
    public boolean running;
    public GridRenderer gridRenderer;
    
    
    // Main method
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        System.out.println("Menu Initialized");
    }
    
    
    // Initialization method
    public void init() {
        // Initialize game entities and other settings here
        System.out.println("Init");
        gridRenderer = new GridRenderer(camera);

        
        // Create game window
        JFrame frame = new JFrame("Game"); // Create a new JFrame object to serve as the game window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the close operation for the window
        frame.setLayout(new BorderLayout()); // Set the layout for the window
        frame.getContentPane().add(gridRenderer, BorderLayout.CENTER); //Add the GridRenderer to the content pane of the window
        frame.add(gridRenderer, BorderLayout.CENTER);
        frame.pack(); // Pack the window to ensure proper sizing
        frame.setVisible(true); // Make the window visible
        
        
        // Add key listener for user input
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gridRenderer.handleKeyPress(e); // Call the handleKeyPress method in the GridRenderer class to handle user input
            }
        });
        
        
        // Add component listener for window resizing
        gridRenderer.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            initializePositions(); // Call the initializePositions method to update the positions of the circle and camera based on the new window size
        }
        });
        
        // Add MouseWheelListener for zooming
        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double zoomFactor = 0.1;
                if (e.getWheelRotation() < 0) {
                    camera.setZoom(Math.min(camera.getZoom() + zoomFactor, 3.0)); // Limit the maximum zoom level
                } else {
                    camera.setZoom(Math.max(camera.getZoom() - zoomFactor, 0.2)); // Limit the minimum zoom level
                }
                gridRenderer.repaint();
            }
        });
        
        
        // Start game loop in seperate thread
        running = true;
        new Thread(this::gameLoop).start();
    }


    // Game loop method
    /**
    * This game loop is responsible for updating and rendering the game entities. It works by calculating the time difference
    * since the last update and adding it to a delta variable. If the delta time is greater than or equal to 1, the game entities
    * are updated and the delta time is decremented. The game entities are then rendered. This loop runs continuously while the 
    * running flag is set to true, ensuring that the game is constantly updating and rendering.
    */
    public void gameLoop() {
        System.out.println("Game Loop Initialized");
        final double updateTime = 1_000_000_000.0 / 60.0; // 60 updates per second
        double lastUpdateTime = System.nanoTime(); 
        double delta = 0;
        while (running) {
            double now = System.nanoTime();
            delta += (now - lastUpdateTime) / updateTime;
            lastUpdateTime = now;

            while (delta >= 1) {
                update(); // Update game entities
                delta--;
            }
            render(); // Render the game entities
            //sync(); // Synchronize the game loop if needed
        }
    }

    
    public void update() {
        // Update game entities/camera here
        try {
            int screenWidth = gridRenderer.getScreenWidth();
            int screenHeight = gridRenderer.getScreenHeight();
            camera.follow(GridRenderer.getCircleX(), GridRenderer.getCircleY(), screenWidth, screenHeight);
        }
        catch(Exception NullPointerException){
            System.out.println("Null Pointer");
        }
    }

    
    // Render game entities method
    public void render() {
        try {
           gridRenderer.repaint();
        }
        catch (Exception NullPointerException) {
            System.out.println("Null Pointer");
        }
    }

    
    // Synchronize game loop method
    public void sync() {
        // Synchronize game loop here, if needed
    }
    
    
    // Method to initialize/update circle and camera positions
    public void initializePositions() {
        // Set the initial circle position based on the camera's position
        GridRenderer.circleX = camera.getX() + gridRenderer.getWidth() / 2;
        GridRenderer.circleY = camera.getY() + gridRenderer.getHeight() / 2;

        // Set the initial camera position based on the gridRenderer's size
        int screenWidth = gridRenderer.getWidth();
        int screenHeight = gridRenderer.getHeight();
        camera.follow(GridRenderer.getCircleX(), GridRenderer.getCircleY(), screenWidth, screenHeight);
    }
}
