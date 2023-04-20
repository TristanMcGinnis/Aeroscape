// Import necessary libraries
package team2.aeroscape;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;
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
    private AudioEngine audioEngine;
    private KeyboardInputManager keyboardManager;
    private MouseInputManager mouseManager;
    private boolean running;
    private boolean isZooming;


    public Aeroscape(String playerName) {
        player = new Player(3000, 3000);
        camera = new Camera(0, 0, 1.5);
        inventory = new Inventory();
        levelData = new LevelData(playerName, 1, 0, new int[10], new int[100][100]);
        gridRenderer = new GridRenderer(camera, player, levelData, inventory);
        audioEngine = new AudioEngine();
        keyboardManager = new KeyboardInputManager();
        mouseManager = new MouseInputManager();
        System.out.println("Player name = " + playerName);
        running = true;
    }
    
    public static void main(String[] args) {
        new MainMenu();
        System.out.println("Menu Initialized");
    }
    
    
    // Initialization method
    public void init() {
        // Initialize game entities and other settings here
        System.out.println("Init");

        audioEngine.loadAudio("MainMusic", "src/main/resources/sfx/MainMusic.wav");
        audioEngine.loopSound2D("MainMusic", -45.0f, Clip.LOOP_CONTINUOUSLY);
        JFrame frame = createGameWindow();
        addListeners(frame);
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
        frame.addKeyListener(keyboardManager);
        frame.addMouseListener(mouseManager);
        gridRenderer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                initializePositions();
            }
        });
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
        final double updateTime = 1000/60; //60 Updates per second
        double lastUpdateTime = System.currentTimeMillis(); 
        double delta = 0;
        
        
        while (running) {
            double now = System.currentTimeMillis();
            delta = (now - lastUpdateTime);
            lastUpdateTime = now;
            
            
            update(updateTime);
            while (delta < updateTime) {
                delta = System.currentTimeMillis() - lastUpdateTime;
            }
            SwingUtilities.invokeLater(() -> {
                render();
            });
            //sync(); // Synchronize the game loop if needed
        }
    }


    public void update(double delta) {
        // Update game entities/camera here
        try {
            int screenWidth = gridRenderer.getScreenWidth();
            int screenHeight = gridRenderer.getScreenHeight();
          
            int step = (int) (5 + camera.getZoom());

            if (keyboardManager.isUp()) {
                player.setVelY(-step);
            } else if (keyboardManager.isDown()) {
                player.setVelY(step);
            } else {
                player.stopY();
            }

            if (keyboardManager.isLeft()) {
                player.setVelX(-step);
            } else if (keyboardManager.isRight()) {
                player.setVelX(step);
            } else {
                player.stopX();
            }
            
            
            

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

            MouseEvent clickEvent = mouseManager.getMouseClickEvent();
            if (clickEvent != null) {
                if (clickEvent.getButton() == MouseEvent.BUTTON1) { // Left mouse button
                    gridRenderer.handleMinerPlacement(clickEvent);
                } else if (clickEvent.getButton() == MouseEvent.BUTTON3) { // Right mouse button
                    gridRenderer.handleSmelterPlacement(clickEvent);
                }
            }

            
        } catch (Exception NullPointerException) {
            System.out.println("Null Pointer");
        }
        for (Miner miner : levelData.getMiners()) {
            miner.update();
        }      
        
        for (Smelter smelter : levelData.getSmelters()) {
            smelter.update();
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
