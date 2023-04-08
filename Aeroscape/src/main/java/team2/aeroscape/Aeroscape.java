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
    private Player player;
    private Camera camera;
    private GridRenderer gridRenderer;
    private Inventory inventory;
    public boolean running;
    private boolean isZooming;

    
    public Aeroscape() {
        player = new Player(300, 300);
        camera = new Camera(0, 0, 1.0);
        inventory = new Inventory();
    }
    
    // Main method
    public static void main(String[] args) {
        Aeroscape aeroscape = new Aeroscape();
        MainMenu mainMenu = new MainMenu();
        System.out.println("Menu Initialized");
    }
    
    
    // Initialization method
    public void init() {
        // Initialize game entities and other settings here
        System.out.println("Init");
        gridRenderer = new GridRenderer(camera, player);

        
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
                gridRenderer.handleKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gridRenderer.handleKeyPress(e);
            }
        });
        
        
        // Add component listener for window resizing
        gridRenderer.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            initializePositions(); // Call the initializePositions method to update the positions of the circle and camera based on the new window size
        }
        });
        
        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double zoomFactor = 0.1;
                if (e.getWheelRotation() < 0) {
                    camera.setZoom(Math.min(camera.getZoom() + zoomFactor, 3.0)); // Limit the maximum zoom level
                } else {
                    camera.setZoom(Math.max(camera.getZoom() - zoomFactor, 0.2)); // Limit the minimum zoom level
                }

                int screenWidth = gridRenderer.getScreenWidth();
                int screenHeight = gridRenderer.getScreenHeight();
                double newX = player.getX() * camera.getZoom() - (screenWidth / 2);
                double newY = player.getY() * camera.getZoom() - (screenHeight / 2);

                camera.setX((int) newX);
                camera.setY((int) newY);

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
                update(delta); // Update game entities
                delta--;
            }
            render(); // Render the game entities
            //sync(); // Synchronize the game loop if needed
        }
    }


    public void update(double delta) {
        // Update game entities/camera here
        try {
            int screenWidth = gridRenderer.getScreenWidth();
            int screenHeight = gridRenderer.getScreenHeight();

            player.update();

            double lerpFactor = 0.1; // Adjust this value for smoother or more responsive movement (0.0 to 1.0)
            double newX, newY;
            if (isZooming) {
                newX = player.getX() * camera.getZoom() - (screenWidth / 2);
                newY = player.getY() * camera.getZoom() - (screenHeight / 2);
            } else {
                newX = Camera.lerp(camera.getX(), player.getX() * camera.getZoom() - (screenWidth / 2), lerpFactor);
                newY = Camera.lerp(camera.getY(), player.getY() * camera.getZoom() - (screenHeight / 2), lerpFactor);
            }

            camera.setX((int) newX);
            camera.setY((int) newY);

        } catch (Exception NullPointerException) {
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
