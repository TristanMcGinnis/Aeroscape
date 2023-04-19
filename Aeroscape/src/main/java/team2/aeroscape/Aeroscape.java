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
    private LevelData levelData;
    private boolean running;
    private boolean isZooming;

    
    public Aeroscape() {
        player = new Player(300, 300);
        camera = new Camera(0, 0, 1.0);
        inventory = new Inventory();
        levelData = new LevelData("defaultPlayer", 1, 0, new int[10], new int[100][100]);
    }
    
    
    public static void main(String[] args) {
        Aeroscape aeroscape = new Aeroscape();
        MainMenu mainMenu = new MainMenu();
        System.out.println("Menu Initialized");
    }
    
    
    // Initialization method
    public void init() {
        // Initialize game entities and other settings here
        System.out.println("Init");
        gridRenderer = new GridRenderer(camera, player, levelData, inventory);

        
        JFrame frame = createGameWindow();
        addListeners(frame);
       

        running = true;
        new Thread(this::gameLoop).start();
    }
    

    private JFrame createGameWindow() {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(gridRenderer, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    private void addListeners(JFrame frame) {
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
        
        gridRenderer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                initializePositions();
            }
        });
        /*
        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleMouseWheel(e);
            }
        });
        */
    }

    private void handleMouseWheel(MouseWheelEvent e) {
        double zoomFactor = 0.1;
        if (e.getWheelRotation() < 0) {
            camera.setZoom(Math.min(camera.getZoom() + zoomFactor, 3.0));
        } else {
            camera.setZoom(Math.max(camera.getZoom() - zoomFactor, 0.2));
        }

        updateCameraPosition();

        gridRenderer.repaint();
    }
    
    private void updateCameraPosition() {
        int screenWidth = gridRenderer.getScreenWidth();
        int screenHeight = gridRenderer.getScreenHeight();
        double newX = player.getX() * camera.getZoom() - (screenWidth / 2);
        double newY = player.getY() * camera.getZoom() - (screenHeight / 2);

        camera.setX((int) newX);
        camera.setY((int) newY);
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
        final double updateTime = 1000 / 60.0; // 60 updates per second in milliseconds
        double lastUpdateTime = System.currentTimeMillis(); 
        double delta = 0;
        
        
        while (running) {
            double now = System.currentTimeMillis();
            delta = (now - lastUpdateTime) / updateTime;
            lastUpdateTime = now;
            
            
//            while(delta > 1) {
//                update(delta);
//                delta--;
//            }
            //Changed ths^ such that we are only updating in the update() 
            //method with a fixed delta and only once per update
            update(updateTime);
            while(delta < updateTime) {
                delta = System.currentTimeMillis() - lastUpdateTime;
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
        
        //In theory shouldn't need this. gridRenderer should update all tiles or more effeciently only building types
        for (Miner miner : levelData.getMiners()) {
            miner.update();
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
        // Set the initial camera position based on the player's position
        int screenWidth = gridRenderer.getWidth();
        int screenHeight = gridRenderer.getHeight();
        camera.follow(player.getX(), player.getY(), screenWidth, screenHeight);
    }
}
